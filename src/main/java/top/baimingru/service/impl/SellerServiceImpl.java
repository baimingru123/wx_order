package top.baimingru.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.baimingru.dataobject.SellerInfo;
import top.baimingru.repository.SellInfoRepository;
import top.baimingru.service.SellerService;

/**
 * @author bmr
 * @time 2019-01-21 13:53
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        SellerInfo sellerInfo=repository.findByOpenid(openid);
        return sellerInfo;
    }
}
