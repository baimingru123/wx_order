package top.baimingru.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.OrderDetail;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.OrderStatusEnum;
import top.baimingru.enums.PayStatusEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-16 14:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID="bmr1008";

    private final String ORDER_ID="f5387d226e654a28abd4ee5634d8abf2";

    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerAddress("绿地中央广场B座");
        orderDTO.setBuyerName("柏明儒");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("18754190709");

        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("123459");
        o1.setProductQuantity(1);

//        OrderDetail o2=new OrderDetail();
//        o2.setProductId("123457");
//        o2.setProductQuantity(3);

        orderDetailList.add(o1);
//        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result=orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
        Assert.assertNotNull(orderDTO);

    }

    @Test
    public void findOne() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}",orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request=new PageRequest(0,3);
        Page<OrderDTO> orderDTOPage=orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(Integer.valueOf(result.getOrderStatus()),Integer.valueOf(OrderStatusEnum.CANCEL.getCode()));
    }

    @Test
    public void finish() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(Integer.valueOf(result.getOrderStatus()),Integer.valueOf(OrderStatusEnum.FINISHED.getCode()));

    }

    @Test
    public void paid() {
        OrderDTO orderDTO=orderService.findOne(ORDER_ID);
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(Integer.valueOf(result.getPayStatus()),Integer.valueOf(PayStatusEnum.SUCCESS.getCode()));
    }

    @Test
    public void findListAllTest() {
        PageRequest request=new PageRequest(0,5);
        Page<OrderDTO> orderDTOPage=orderService.findList(request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }
}