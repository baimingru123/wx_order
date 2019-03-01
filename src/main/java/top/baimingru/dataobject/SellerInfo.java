package top.baimingru.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author bmr
 * @time 2019-01-21 13:42
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellId;

    private String username;

    private String password;

    private String openid;
}
