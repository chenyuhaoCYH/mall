package com.cyh.mall.product.dao;

import com.cyh.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:14
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
