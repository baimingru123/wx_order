package top.baimingru.form;

import lombok.Data;

/**
 * @author bmr
 * @time 2019-01-21 11:26
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名称*/
    private String categoryName;

    /** 类目编号*/
    private Integer categoryType;
}
