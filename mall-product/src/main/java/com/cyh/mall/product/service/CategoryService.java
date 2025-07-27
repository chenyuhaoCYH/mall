package com.cyh.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.common.utils.R;
import com.cyh.mall.product.common.response.CategoryDTO;
import com.cyh.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:14
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryDTO> listWithTree(Map<String, Object> params);

    R removeCategoryByIds(List<String> deleteCatIds);

    R saveCategory(CategoryEntity category);

    boolean updateBatch(List<CategoryEntity> categoryList);
}

