package top.baimingru.dto;

import lombok.Data;

/**
 * @author bmr
 * @time 2019-01-16 14:29
 */
@Data
public class CartDTO {

    /** 商品id. */
    private String productId;

    /** 商品数量. */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
