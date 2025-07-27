package com.cyh.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.cyh.mall.common.constant.ResponseConstant;
import com.cyh.mall.product.common.request.CategoryRequestDTO;
import com.cyh.mall.product.common.response.CategoryDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyh.mall.product.entity.CategoryEntity;
import com.cyh.mall.product.service.CategoryService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.common.utils.R;



/**
 * 商品三级分类
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:14
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryEntity category = new CategoryEntity();
        BeanUtils.copyProperties(categoryRequestDTO,category);
        return categoryService.saveCategory(category);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
        boolean flag = categoryService.updateById(category);
        return flag?R.ok(ResponseConstant.Update.SUCCESS):R.error(ResponseConstant.Update.FAIL);
    }

    @RequestMapping("/updateBatch")
    public R updateBatch(@RequestBody List<CategoryEntity> categoryList){
        if (categoryService.updateBatch(categoryList)){
          return  R.ok(ResponseConstant.Update.SUCCESS);
        }
        else {
           return R.error(ResponseConstant.Update.FAIL_PARTITION);
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody CategoryRequestDTO categoryRequestDTO){
        if (categoryRequestDTO==null||CollectionUtils.isEmpty(categoryRequestDTO.getDeleteCatIds())){
            return R.ok();
        }
		return categoryService.removeCategoryByIds(categoryRequestDTO.getDeleteCatIds());
    }

    @GetMapping("/listTree")
    //@ApiOperation("分类列表带等级")
    public R listTree(@RequestParam Map<String, Object> params){
        List<CategoryDTO> result=categoryService.listWithTree(params);
        return R.ok().put("data",result);
    }

}
