package top.baimingru.service;

import top.baimingru.dto.OrderDTO;

/**
 * @author bmr
 * @time 2019-01-17 15:20
 */
public interface BuyerService {

    //查询单订单详情
    OrderDTO findOrderOneDetail(String openid,String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);

}
