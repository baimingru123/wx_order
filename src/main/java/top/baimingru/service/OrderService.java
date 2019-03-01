package top.baimingru.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.baimingru.dataobject.OrderMaster;
import top.baimingru.dto.OrderDTO;

/**
 * @author bmr
 * @time 2019-01-11 17:22
 */
public interface OrderService {

    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 查询单个订单
     */
    OrderDTO findOne(String orderId);

    /**
     * 查询单个用户的订单列表
     */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消订单
     */
    OrderDTO cancel(OrderDTO orderDTO);

    /**
     * 完结订单
     */
    OrderDTO finish(OrderDTO orderDTO);

    /**
     * 支付订单
     */
    OrderDTO paid(OrderDTO orderDTO);

    /**
     * 查询所有用户的订单列表
     */
    Page<OrderDTO> findList( Pageable pageable);
}
