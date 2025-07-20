package com.cyh.mall.coupon.controller;

import java.util.Arrays;
import java.util.Map;


import com.cyh.mall.coupon.rpc.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyh.mall.coupon.entity.CouponEntity;
import com.cyh.mall.coupon.service.CouponService;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.common.utils.R;



/**
 * 优惠券信息
 *
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 15:48:13
 */
//动态刷新配置
@RefreshScope
@RestController
@RequestMapping("coupon/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    ProductService productService;

    @Value("${user.name:chen}")
    private String name;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = couponService.queryPage(params);

       // return R.ok().put("page", page);
        return productService.queryAllBrand();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", name);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
