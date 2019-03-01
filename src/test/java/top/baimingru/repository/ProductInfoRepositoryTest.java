package top.baimingru.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.ProductInfo;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-11 13:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest() {
        ProductInfo info=new ProductInfo();
        info.setProductId("123456");
        info.setProductName("皮蛋粥");
        info.setProductPrice(new BigDecimal(3.2));
        info.setProductStock(100);
        info.setProductDescription("很好喝的粥");
        info.setProductIcon("http://xxx.jpg");
        info.setProductStatus(0);
        info.setCategoryType(1);
        ProductInfo result=repository.save(info);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> result=repository.findByProductStatus(0);
        Assert.assertNotEquals(0,result.size());
    }

}