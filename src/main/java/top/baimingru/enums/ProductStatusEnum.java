package top.baimingru.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author bmr
 * @time 2019-01-11 14:17
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"在架"),
    DOWN(1,"下架")
    ;

    private Integer code;

    private  String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
