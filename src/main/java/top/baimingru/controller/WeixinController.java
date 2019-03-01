package top.baimingru.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 手动编写    暂时保留
 * @author bmr
 * @time 2019-01-17 17:33
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(String code){
        log.info("进入auth方法,code:"+code);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx83a902051973be3e&secret=2b9aa1895e5d6ef8fe1c3043298ad593&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        log.info("response={}",response);
    }
}
