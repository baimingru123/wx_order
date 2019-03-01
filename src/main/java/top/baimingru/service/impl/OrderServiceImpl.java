package top.baimingru.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.baimingru.converter.OrderMaster2OrderDTOConverter;
import top.baimingru.dataobject.OrderDetail;
import top.baimingru.dataobject.OrderMaster;
import top.baimingru.dataobject.ProductInfo;
import top.baimingru.dto.CartDTO;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.OrderStatusEnum;
import top.baimingru.enums.PayStatusEnum;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.repository.OrderDetailRepository;
import top.baimingru.repository.OrderMasterRepository;
import top.baimingru.service.*;
import top.baimingru.utils.KeyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bmr
 * @time 2019-01-11 17:32
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId=KeyUtil.genUniqueKey();
        BigDecimal orderAmount=new BigDecimal(0);
        //1.查询商品(数量，价格)
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo=productInfoService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价
            orderAmount=orderAmount.add(productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));

            //订单详情入库
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }


        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣除库存
        List<CartDTO> cartDTOList= orderDTO.getOrderDetailList().stream().
                map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).
                collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        //5.往客户端发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        //1.根据openid获取用户的所有订单
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        //判断订单状态
        if(!(orderDTO.getOrderStatus()==OrderStatusEnum.NEW.getCode())){
            log.info("【取消订单】订单状态不正确，orderId:{},orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【取消订单】订单状态修改失败");
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }

        //返回库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e->new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //如果用户已经支付，需要退款
        if(orderDTO.getPayStatus()==PayStatusEnum.SUCCESS.getCode()){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!(orderDTO.getOrderStatus()==OrderStatusEnum.NEW.getCode())){
            log.info("【完结订单】订单状态不正确，orderId:{},orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【完结订单】订单状态修改失败");
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }

        //推送微信模板消息
//        pushMessageService.orderStatusUpdate(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!(orderDTO.getOrderStatus()==OrderStatusEnum.NEW.getCode())){
            log.info("【成功支付订单】订单状态不正确，orderId:{},orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!(orderDTO.getPayStatus()==PayStatusEnum.WAIT.getCode())){
            log.info("【成功支付订单】订单支付状态不正确，orderId:{},orderStatus:{}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改订单的支付状态
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult=orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.info("【成功支付订单】订单状态修改失败");
            throw new SellException(ResultEnum.ORDER_STATUS_UPDATE_ERROR);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList( Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
