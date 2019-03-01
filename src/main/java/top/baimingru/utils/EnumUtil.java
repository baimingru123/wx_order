package top.baimingru.utils;

import top.baimingru.enums.CodeEnum;

/**
 * @author bmr
 * @time 2019-01-19 14:23
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
