package top.baimingru.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.baimingru.dataobject.ProductInfo;
import top.baimingru.dto.CartDTO;

import java.util.List;

/**
 * 商品service
 * @author bmr
 * @time 2019-01-11 14:07
 */
public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有上架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有商品
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     */
    ProductInfo onSale(String productId);

    /**
     * 下架
     */
    ProductInfo offSale(String productId);
}
