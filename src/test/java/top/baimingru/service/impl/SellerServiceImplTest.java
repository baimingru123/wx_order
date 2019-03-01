package top.baimingru.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.SellerInfo;
import top.baimingru.service.SellerService;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-21 13:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    private static final String OPENID="abc";

    @Autowired
    private SellerService sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo result=sellerService.findSellerInfoByOpenid(OPENID);
        Assert.assertEquals(OPENID,result.getOpenid());
    }
}