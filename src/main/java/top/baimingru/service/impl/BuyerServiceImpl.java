package top.baimingru.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.service.BuyerService;
import top.baimingru.service.OrderService;

/**
 * @author bmr
 * @time 2019-01-17 15:22
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOneDetail(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        if(orderDTO==null){
            log.error("【取消订单】该订单不存在，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单】订单openid不一致。openid:{},orderDTO:{}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
