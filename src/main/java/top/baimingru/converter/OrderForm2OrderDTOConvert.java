package top.baimingru.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import top.baimingru.dataobject.OrderDetail;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bmr
 * @time 2019-01-17 11:56
 */
@Slf4j
public class OrderForm2OrderDTOConvert {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson=new Gson();
        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.info("【对象转换】错误，String={}",orderForm.getItems());
            throw new SellException(ResultEnum.CONVERT_ERROR.getCode(),"items非json格式");
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
