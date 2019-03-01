package top.baimingru.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bmr
 * @time 2019-01-11 16:49
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {
    @Id
    /** 主键. */
    private String detailId;

    /** 订单主键. */
    private String orderId;

    /** 商品主键. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品图片. */
    private String productIcon;

    /** 创建时间. */
    @JsonIgnore
    private Date createTime;

    /** 更新时间. */
    @JsonIgnore
    private Date updateTime;
}
