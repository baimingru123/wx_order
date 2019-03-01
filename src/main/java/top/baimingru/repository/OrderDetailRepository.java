package top.baimingru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.baimingru.dataobject.OrderDetail;

import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 16:53
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    List<OrderDetail> findByOrderId(String orderId);
}
