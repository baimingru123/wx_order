package top.baimingru.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.OrderMaster;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-11 16:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    private final String OPENOD="bmr1008";
    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123458");
        orderMaster.setBuyerName("杜姐");
        orderMaster.setBuyerPhone("15966699226");
        orderMaster.setBuyerAddress("金科城");
        orderMaster.setBuyerOpenid("duyan1008");
        orderMaster.setOrderAmount(new BigDecimal(63.6));
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request=new PageRequest(0,3);
        Page<OrderMaster> result=repository.findByBuyerOpenid(OPENOD,request);
        Assert.assertNotEquals(0,result.getContent().size());
    }


}