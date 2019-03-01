package top.baimingru.constant;

/**
 * redis 常量
 * @author bmr
 * @time 2019-01-21 15:39
 */
public interface RedisConstant {

    String TOKEN_PREFIX="token_%s";
    Integer EXPIRE=7200;//2小时  1*60*60*2=7200  秒为单位
}
