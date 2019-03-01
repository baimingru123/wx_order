package top.baimingru.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.baimingru.dataobject.ProductCategory;
import top.baimingru.exception.SellException;
import top.baimingru.form.CategoryForm;
import top.baimingru.service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author bmr
 * @time 2019-01-21 11:05
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList=categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("/category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,Map<String,Object> map){
        if(categoryId !=null){
            ProductCategory productCategory=categoryService.findOne(categoryId);
            map.put("category",productCategory);
        }

        return new ModelAndView("category/index",map);
    }

    /**
     * 新增/修改
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult,Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory category=new ProductCategory();

        try {
            if(form.getCategoryId()!=null){
                category=categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form,category);
            categoryService.save(category);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","操作成功");
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);

    }
}
