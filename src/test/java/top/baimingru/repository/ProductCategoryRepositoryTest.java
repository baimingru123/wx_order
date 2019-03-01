package top.baimingru.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import top.baimingru.dataobject.ProductCategory;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author bmr
 * @time 2019-01-11 9:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory category=repository.findOne(1);
        System.out.println(category);
    }

    @Test
    @Transactional
    public void saveTest(){
//        ProductCategory category=repository.findOne(2);
//        if(category!=null){
//            category.setCategoryType(10);
//            repository.save(category);
//        }
        ProductCategory category=new ProductCategory("男生最爱",3);
        ProductCategory result=repository.save(category);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list=Arrays.asList(2,3,4);
        List<ProductCategory> result=repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }

}