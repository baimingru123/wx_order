package top.baimingru.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author bmr
 * @time 2019-01-17 11:42
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "收货地址必填")
    private String address;

    @NotEmpty(message = "openid必填")
    private String openid;

    @NotEmpty(message = "items必填")
    private String items;
}
