package top.baimingru.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author bmr
 * @time 2019-01-21 10:10
 */
@Data
public class ProductForm {
    /**
     * 主键
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private Integer productStock;

    /**
     * 商品描述
     */
    private String productDescription;

    /**
     * 商品图片
     */
    private String productIcon;


    /**
     * 类目编号
     */
    private Integer categoryType;
}
