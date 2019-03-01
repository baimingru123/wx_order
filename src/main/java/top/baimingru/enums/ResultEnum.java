package top.baimingru.enums;

import lombok.Getter;

/**
 * @author bmr
 * @time 2019-01-16 13:58
 */
@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),

    PARAM_ERROR(1,"参数不正确"),

    CONVERT_ERROR(2,"转换异常"),

    CART_IS_EMPTY(3,"购物车信息为空"),

    ORDER_OWNER_ERROR(4,"该订单不属于当前用户"),

    WECHAT_ERROR(5,"微信方面异常"),

    LOGIN_FAIL(6,"登录失败，登录信息不正确"),

    LOGOUT_SUCCESS(7,"登出成功"),

    PRODUCT_NOT_EXIST(10,"商品不存在"),

    PRODUCT_STOCK_ERROR(11,"库存不正确"),

    ORDER_NOT_EXIST(12,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(12,"订单详情不存在"),

    ORDER_STATUS_ERROR(13,"订单状态不正确"),

    ORDER_STATUS_UPDATE_ERROR(14,"订单状态修改失败"),

    ORDER_PAY_STATUS_ERROR(15,"订单支付状态不正确"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(16,"微信支付异步通知金额校验不通过"),

    ORDER_CANCEL_SUCCESS(17,"订单取消成功"),

    ORDER_FINISH_SUCCESS(18,"订单完结成功"),

    PRODUCT_STATUS_ERROR(19,"商品状态错误"),

    PRODUCT_ON_SALE_SUCCESS(20,"商品上架成功"),

    PRODUCT_OFF_SALE_SUCCESS(21,"商品下架成功"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
