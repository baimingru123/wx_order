package top.baimingru.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.baimingru.dataobject.ProductCategory;
import top.baimingru.dataobject.ProductInfo;
import top.baimingru.enums.ProductStatusEnum;
import top.baimingru.enums.ResultEnum;
import top.baimingru.exception.SellException;
import top.baimingru.form.ProductForm;
import top.baimingru.service.CategoryService;
import top.baimingru.service.ProductInfoService;
import top.baimingru.utils.KeyUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品管理控制层
 * @author bmr
 * @time 2019-01-19 17:06
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;
    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "10") Integer size, Map<String,Object> map){
        PageRequest request=new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage=productInfoService.findAll(request);
//        log.info("商品列表:{}",CollectionUtils.toArray(productInfoPage.getContent()));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return  new ModelAndView("product/list",map);
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/on_sale")
    public ModelAndView onSale(String productId,Map<String,Object> map){

        try {
            productInfoService.onSale(productId);
        }catch (SellException e){
            log.info("【卖家端上架商品】发生异常:{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.PRODUCT_ON_SALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }


    /**
     * 下架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/off_sale")
    public ModelAndView offSale(String productId,Map<String,Object> map){

        try {
            productInfoService.offSale(productId);
        }catch (SellException e){
            log.info("【卖家端下架商品】发生异常:{}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.PRODUCT_OFF_SALE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,Map<String,Object> map){
//        System.out.println("productId:"+productId);
        if(!StringUtils.isEmpty(productId)){
           ProductInfo productInfo= productInfoService.findOne(productId);
           map.put("productInfo",productInfo);
        }

        //查询所有类目
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("categoryList",categoryList);

//        System.out.println("productInfo:"+map.get("productInfo"));

        return new ModelAndView("product/index",map);
    }

    /**
     * 保存/更新
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
//    @CachePut(cacheNames = "product",key="123")
    @CacheEvict(cacheNames = "product",key="123")
    public ModelAndView save(@Valid ProductForm productForm, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        ProductInfo productInfo=new ProductInfo();
        try {
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo=productInfoService.findOne(productForm.getProductId());
            }else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);

        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }

        map.put("msg","操作成功");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
