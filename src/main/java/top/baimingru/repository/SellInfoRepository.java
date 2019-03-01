package top.baimingru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.baimingru.dataobject.SellerInfo;

/**
 * @author bmr
 * @time 2019-01-21 13:44
 */
public interface SellInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);
}
