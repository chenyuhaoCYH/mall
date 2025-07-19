package com.cyh.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 12:28:13
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

