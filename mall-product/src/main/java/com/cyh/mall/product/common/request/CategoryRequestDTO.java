package com.cyh.mall.product.common.request;

import lombok.Data;

import java.util.List;

@Data
public class CategoryRequestDTO {

    private List<String> deleteCatIds;

    private Long catId;

    private String name;

    private Integer catLevel;

    private Long parentCid;

    private Integer showStatus;

    private Integer sort;

    private String icon;

    private String productUnit;
}
