package com.cyh.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:14
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

