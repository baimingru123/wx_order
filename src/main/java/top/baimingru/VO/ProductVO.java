package top.baimingru.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品(包含类目)，用于给前端返回数据
 * @author bmr
 * @time 2019-01-11 15:37
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 5345109752551443007L;

    @JsonProperty("name")
    private String category_name;

    @JsonProperty("type")
    private Integer category_type;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
