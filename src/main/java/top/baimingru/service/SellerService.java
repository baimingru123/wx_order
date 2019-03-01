package top.baimingru.service;

import top.baimingru.dataobject.SellerInfo;

/**
 * 卖家端service
 * @author bmr
 * @time 2019-01-21 13:51
 */
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
