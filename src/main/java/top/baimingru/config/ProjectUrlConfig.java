package top.baimingru.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author bmr
 * @time 2019-01-21 14:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    /** 微信公众平台授权url. */
    private String wechatMpAuthorize;

    /** 微信开放平台授权url. */
    private String wechatOpenAuthorize;

    /** 本项目的url. */
    private String systemUrl;
}
