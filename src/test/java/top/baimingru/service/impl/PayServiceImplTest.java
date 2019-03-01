package top.baimingru.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dto.OrderDTO;
import top.baimingru.service.OrderService;
import top.baimingru.service.PayService;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-18 14:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    private static final String ORDER_ID="97d8908d1c3c461682400581e9a2f4fb";

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO=orderService.findOne("da4488291d2143cda43259536b129191");
        payService.refund(orderDTO);
    }

}