package top.baimingru.service;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author bmr
 * @time 2019-01-22 14:31
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * 逻辑处理：1.调用redis中的setnx命令，如果不存在该key，就将value放进去，如果存在，则什么也不做
     *          2.如果该key已经存在，但有可能是因为死锁造成的未释放锁资源，所以应该先获取该key的value值是否小于当前时间，
     *            如果小于，表明此锁已过期
     *         3.此时虽然已经判断出锁资源已过期，但是却不能直接将锁资源给访问的线程，因为此时可能有多个线程同时进行访问，
     *          还需要判断过期锁中的value是否和刚进入该方法时获取的value值一致。如果一致，表明该线程第一个获取到了锁资源，
     *          可以返回true，其他的线程就需要继续排队等待。
     * @param key
     * @param value  当前时间+超时时间
     * @return
     */
    public boolean lock(String key,String value){
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }

        String currentValue=redisTemplate.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue)<System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue=redisTemplate.opsForValue().getAndSet(key,value);
            if(!StringUtils.isEmpty(oldValue)
                    && oldValue.equals(currentValue)){
                return true;
            }
        }

        return false;
    }


    /**
     * 释放锁资源
     * 判读是否是一个锁资源，如果是才进行释放，如果不是，不能随意释放，因为应用该锁资源的线程还正在使用中。
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.info("【redis分布式锁】解锁异常：{}",e);
        }
    }
}
