package top.baimingru.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信相关账号配置信息
 * @author bmr
 * @time 2019-01-17 18:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /** 公众平台AppId. */
    private String mpAppId;

    /** 公众平台AppSecret. */
    private String mpAppSecret;

    /** 开放平台AppId. */
    private String openAppId;

    /** 开放平台AppSecret. */
    private String openAppSecret;

    /** 支付平台商户号. */
    private String mchId;

    /** 支付平台商户秘钥. */
    private String mchKey;

    /** 支付平台商户证书路径. */
    private String keyPath;

    /** 微信支付异步通知地址. */
    private String notifyUrl;

    /** 微信模板id*/
    private Map<String,String> templateId;
}
