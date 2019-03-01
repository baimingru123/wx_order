package top.baimingru.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.service.OrderService;
import top.baimingru.service.PayService;
import top.baimingru.utils.JsonUtil;
import top.baimingru.utils.MathUtil;

import java.math.BigDecimal;

/**
 * @author bmr
 * @time 2019-01-18 13:54
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private  static final String ORDER_NAME="微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;



    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】发起支付请求参数:{}",JsonUtil.object2Json(payRequest));
        PayResponse payResponse=bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付返回参数:{}",JsonUtil.object2Json(payResponse));
        return  payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】支付结果异步通知:{}",JsonUtil.object2Json(payResponse));

        //bestPay  SDK中已经对微信返回的数据进行了验签和支付状态的校验
        //但是我们需要自己对支付金额进行校验

        //查询订单是否存在
        OrderDTO orderDTO=orderService.findOne(payResponse.getOrderId());
        if(orderDTO==null){
            log.error("【微信支付】支付结果异步通知，订单不存在");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }


        //判断订单是否一致
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】支付结果异步通知，订单金额不一致，orderId={},微信通知金额={},订单金额={}",orderDTO.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderDTO);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】请求参数:{}",JsonUtil.object2Json(refundRequest));
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        RefundResponse refundResponse=bestPayService.refund(refundRequest);
        log.info("【微信退款】返回参数:{}",JsonUtil.object2Json(refundResponse));
        return refundResponse;

    }
}
