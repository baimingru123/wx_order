package top.baimingru.service;

import top.baimingru.dto.OrderDTO;

/**
 * @author bmr
 * @time 2019-01-21 17:53
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息推送
     * @param orderDTO
     */
    void orderStatusUpdate(OrderDTO orderDTO);

}
