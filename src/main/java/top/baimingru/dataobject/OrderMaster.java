package top.baimingru.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import top.baimingru.enums.OrderStatusEnum;
import top.baimingru.enums.PayStatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 16:42
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
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

    /** 订单状态  默认0 新订单. */
    private Integer orderStatus=OrderStatusEnum.NEW.getCode();

    /** 支付状态  默认0 未支付. */
    private Integer payStatus=PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}
