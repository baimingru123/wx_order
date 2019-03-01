package top.baimingru.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import top.baimingru.dataobject.OrderDetail;
import top.baimingru.enums.OrderStatusEnum;
import top.baimingru.enums.PayStatusEnum;
import top.baimingru.utils.EnumUtil;
import top.baimingru.utils.serializer.Date2LongSerializer;
import top.baimingru.utils.serializer.Date2StringSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 17:28
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /** 主键. */
    private String orderId;

    /** 买家姓名. */
    private String buyerName;

    /** 买家电话. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信openId. */
    private String buyerOpenid;

    /** 订单金额. */
    private BigDecimal orderAmount;

    /** 订单状态. */
    private Integer orderStatus;

    /** 支付状态. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2StringSerializer.class)
    private Date updateTime;

    /** 订单详情. */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}
