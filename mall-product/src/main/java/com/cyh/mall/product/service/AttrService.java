package com.cyh.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:14
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

