package top.baimingru.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.baimingru.VO.ProductInfoVO;
import top.baimingru.VO.ProductVO;
import top.baimingru.VO.ResultVO;
import top.baimingru.dataobject.ProductCategory;
import top.baimingru.dataobject.ProductInfo;
import top.baimingru.service.CategoryService;
import top.baimingru.service.ProductInfoService;
import top.baimingru.utils.ResultVOUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品控制层
 * @author bmr
 * @time 2019-01-11 14:51
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key="123",unless = "#result.getCode()!=0")
    public ResultVO list(){
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList=productInfoService.findUpAll();


        //2.查询类目
        //传统方法
//        List<Integer> categoryTypeList=new ArrayList<>();
//        for (ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法(java8中的lambda)
        List<Integer> categoryTypeList=productInfoList.stream()
                .map(e->e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory category:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategory_name(category.getCategoryName());
            productVO.setCategory_type(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfo info:productInfoList){
                if(info.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(info,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
