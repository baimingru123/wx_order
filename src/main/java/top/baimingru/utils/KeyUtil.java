package top.baimingru.utils;

import java.util.UUID;

/**
 * @author bmr
 * @time 2019-01-16 14:17
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

}
