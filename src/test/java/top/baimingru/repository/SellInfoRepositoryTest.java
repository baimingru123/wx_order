package top.baimingru.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.SellerInfo;
import top.baimingru.utils.KeyUtil;

/**
 * @author bmr
 * @time 2019-01-21 13:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellInfoRepositoryTest {

    @Autowired SellInfoRepository repository;

    @Test
    public void saveTest(){
        SellerInfo sellerInfo =new SellerInfo();
        sellerInfo.setSellId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result=repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {
        SellerInfo result=repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}