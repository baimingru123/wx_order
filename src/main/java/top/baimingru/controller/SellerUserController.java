package top.baimingru.controller;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import top.baimingru.config.ProjectUrlConfig;
import top.baimingru.constant.CookieConstant;
import top.baimingru.constant.RedisConstant;
import top.baimingru.dataobject.SellerInfo;
import top.baimingru.enums.ResultEnum;
import top.baimingru.service.SellerService;
import top.baimingru.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author bmr
 * @time 2019-01-21 15:01
 */
@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(String openid, Map<String,Object> map, HttpServletResponse response){
        //1.openid于数据库中的数据进行匹配
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo==null){
            map.put("msg",ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url",projectUrlConfig.getSystemUrl()+"");
            return new ModelAndView("common/error");
        }

        //2.设置token至redis
        String token=UUID.randomUUID().toString();
        Integer expire=RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire,TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,expire);
        return new ModelAndView("redirect:"+projectUrlConfig.getSystemUrl()+"/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map){
        //1.从cookie里查询token
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null){
            //2.清楚redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            //3.清楚cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg",ResultEnum.LOGOUT_SUCCESS);
        map.put("url",projectUrlConfig.getSystemUrl()+"/seller/order/list");
        return new ModelAndView("common/success",map);

    }

}
