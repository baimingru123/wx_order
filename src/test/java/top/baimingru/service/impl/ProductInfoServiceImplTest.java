package top.baimingru.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.baimingru.dataobject.ProductInfo;
import top.baimingru.enums.ProductStatusEnum;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-11 14:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
    }

    @Test
    public void findUpAll() {
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<ProductInfo> result=productInfoService.findAll(request);
        Assert.assertNotEquals(0,result.getSize());
    }

    @Test
    public void save() {
        ProductInfo info=new ProductInfo();
        info.setProductId("123459");
        info.setProductName("茶叶蛋");
        info.setProductPrice(new BigDecimal(0.01));
        info.setProductStock(100);
        info.setProductDescription("很好吃的茶叶蛋");
        info.setProductIcon("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=302764109,424229962&fm=27&gp=0.jpg");
        info.setProductStatus(ProductStatusEnum.DOWN.getCode());
        info.setCategoryType(0);
        ProductInfo result=productInfoService.save(info);
        Assert.assertNotNull(result);
    }
}