package com.cyh.mall.coupon.rpc.product;

import com.cyh.mall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "mall-product")
public interface ProductService {
    /**
     * 需要访问的远程方法
     * @return
     */
    @GetMapping("/product/brand/list")
    public R queryAllBrand();
}
