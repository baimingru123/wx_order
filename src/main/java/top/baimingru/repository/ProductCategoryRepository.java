package top.baimingru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.baimingru.dataobject.ProductCategory;

import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 9:45
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categroyTypeList);
}
