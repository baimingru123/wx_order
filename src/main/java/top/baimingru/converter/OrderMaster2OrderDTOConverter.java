package top.baimingru.converter;

import org.springframework.beans.BeanUtils;
import top.baimingru.dataobject.OrderMaster;
import top.baimingru.dto.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bmr
 * @time 2019-01-16 17:52
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();

        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return  orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
    }
}
