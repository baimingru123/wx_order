package top.baimingru.enums;

import lombok.Getter;

/**
 * @author bmr
 * @time 2019-01-11 16:43
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0,"待支付"),
    SUCCESS(1,"支付成功")
    ;
    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
