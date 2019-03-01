package top.baimingru.service;

import top.baimingru.dataobject.ProductCategory;

import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 11:55
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory category);

}
