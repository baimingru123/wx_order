package top.baimingru.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.baimingru.config.WechatAccountConfig;
import top.baimingru.dto.OrderDTO;
import top.baimingru.service.PushMessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bmr
 * @time 2019-01-21 17:55
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatusUpdate(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage=new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data=Arrays.asList(
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyWord1","微信点餐")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch (WxErrorException e){
            log.error("【微信模板消息发送失败，{}】,e");
        }

    }
}
