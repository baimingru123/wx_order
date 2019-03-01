package top.baimingru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.baimingru.dataobject.ProductInfo;

import java.util.List;

/**
 * @author bmr
 * @time 2019-01-11 13:46
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
