package com.cyh.mall.mallproducet;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyh.mall.product.MallProducetApplication;
import com.cyh.mall.product.entity.BrandEntity;
import com.cyh.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = MallProducetApplication.class)
class MallProducetApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity entity = new BrandEntity();
        entity.setName("魅族");
        brandService.save(entity);
    }

    @Test
    void selectAll() {
        List<BrandEntity> list = brandService.list();
        for (BrandEntity entity : list) {
            System.out.println(entity);
        }
    }

    @Test
    void selectById() {
        List<BrandEntity> list = brandService
                .list(new QueryWrapper<BrandEntity>().eq("brand_id",2));
        for (BrandEntity entity : list) {
            System.out.println(entity);
        }
    }


}
