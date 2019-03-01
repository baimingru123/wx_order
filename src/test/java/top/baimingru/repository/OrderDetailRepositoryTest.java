package top.baimingru.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.OrderDetail;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-11 17:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail detail=new OrderDetail();
        detail.setDetailId("2");
        detail.setOrderId("123457");
        detail.setProductId("123457");
        detail.setProductIcon("http://xxx.jpg");
        detail.setProductName("皮蛋粥");
        detail.setProductPrice(new BigDecimal(3.2));
        detail.setProductQuantity(1);
        OrderDetail result=repository.save(detail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> result=repository.findByOrderId("123456");
        Assert.assertNotEquals(0,result.size());
    }
}