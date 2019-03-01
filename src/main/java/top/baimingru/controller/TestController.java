package top.baimingru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.baimingru.exception.SellException;
import top.baimingru.service.RedisLock;
import top.baimingru.utils.KeyUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bmr
 * @time 2019-01-22 13:42
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static final int TIMEOUT=10*1000;//超时时间  10s

    @Autowired
    private RedisLock redisLock;

    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;

    static {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products=new HashMap<>();
        stock=new HashMap<>();
        orders=new HashMap<>();

        products.put("123456",10000);
        stock.put("123456",10000);
    }

    @RequestMapping("/query")
    public String queryMap(@RequestParam(value = "productId",defaultValue = "123456") String productId){
        return "可乐鸡翅，限量"+products.get(productId)+"份，还剩:"+
                stock.get(productId)+"份，该商品成功下单的用户数:"+
                orders.size()+"人";
    }


    @RequestMapping("/test")
    public void test(){
        String productId="123456";
        //加锁
        long time=System.currentTimeMillis()+TIMEOUT;
        if(!redisLock.lock(productId,String.valueOf(time))){
            throw new SellException(101,"没有抢到啊");
        }

        //1.查询该商品库存，为0则活动结束
        int stockNum=stock.get(productId);
        if(stockNum==0) {
            throw new SellException(100,"活动结束");
        }else{
            //2.下单
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3.减库存
            stockNum-=1;
            try {
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        stock.put(productId,stockNum);
        //解锁
        redisLock.unlock(productId,String.valueOf(time));

//        return "可乐鸡翅，限量"+products.get(productId)+"份，还剩:"+
//                stock.get(productId)+"份，该商品成功下单的用户数:"+
//                orders.size()+"人";
    }
}
