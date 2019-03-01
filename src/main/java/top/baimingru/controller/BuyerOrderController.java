package top.baimingru.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.baimingru.VO.ResultVO;
import top.baimingru.converter.OrderForm2OrderDTOConvert;
import top.baimingru.dto.OrderDTO;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.form.OrderForm;
import top.baimingru.service.BuyerService;
import top.baimingru.service.OrderService;
import top.baimingru.utils.ResultVOUtil;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bmr
 * @time 2019-01-17 11:35
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String >> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO=OrderForm2OrderDTOConvert.convert(orderForm);

        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("【创建订单】购物车为空");
            throw new SellException(ResultEnum.CART_IS_EMPTY);
        }

        OrderDTO createResult=orderService.create(orderDTO);

        Map<String,String > map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"openid不能为空");
        }

        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,pageRequest);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(String openid,String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询单订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"openid不能为空");
        }

        if(StringUtils.isEmpty(orderId)){
            log.error("【查询单订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"orderId不能为空");
        }

        OrderDTO orderDTO=buyerService.findOrderOneDetail(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(String openid,String orderId){
        if(StringUtils.isEmpty(openid)){
            log.error("【查询单订单详情】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"openid不能为空");
        }

        if(StringUtils.isEmpty(orderId)){
            log.error("【查询单订单详情】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),"orderId不能为空");
        }

        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();

    }
}
