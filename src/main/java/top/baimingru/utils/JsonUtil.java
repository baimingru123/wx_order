package top.baimingru.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author bmr
 * @time 2019-01-18 15:01
 */
public class JsonUtil {

    /**
     * 将object进行json格式化
     * @param object
     * @return
     */
    public static String object2Json(Object object){
        //该方式只能将对象转换为json，但是打印的时候并没有进行格式化
//        Gson gson=new Gson();
//        return gson.toJson(object);


        //该方式将对象转换为json的同时并在打印的时候进行格式化
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=gsonBuilder.create();
        return gson.toJson(object);
    }
}
