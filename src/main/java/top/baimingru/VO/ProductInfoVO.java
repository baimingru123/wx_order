package top.baimingru.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情VO
 * @author bmr
 * @time 2019-01-11 15:42
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -2356075170264253198L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
