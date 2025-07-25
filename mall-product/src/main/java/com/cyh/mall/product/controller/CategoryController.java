package com.cyh.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.cyh.mall.product.common.response.CategoryDTO;
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
    
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    
    public R delete(@RequestBody Long[] catIds){
		categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

    @GetMapping("/listWithTree")
    //@ApiOperation("分类列表带等级")
    public R listWithTree(@RequestParam Map<String, Object> params){
        List<CategoryDTO> result=categoryService.listWithTree(params);
        return R.ok().put("pageResult",result);
    }

}
