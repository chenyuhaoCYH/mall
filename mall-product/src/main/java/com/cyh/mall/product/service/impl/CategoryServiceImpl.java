package com.cyh.mall.product.service.impl;

import com.cyh.mall.product.common.response.CategoryDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyh.mall.common.utils.PageUtils;
import com.cyh.mall.common.utils.Query;

import com.cyh.mall.product.dao.CategoryDao;
import com.cyh.mall.product.entity.CategoryEntity;
import com.cyh.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryDTO> listWithTree(Map<String, Object> params) {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<>());
        if (CollectionUtils.isEmpty(categoryEntities)){
            return Lists.newArrayList();
        }
        Map<Long, CategoryDTO> categoryDTOMap = categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getShowStatus()==1)
                .map(categoryEntity -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(categoryEntity, categoryDTO);
            return categoryDTO;
        }).collect(Collectors.toMap(CategoryDTO::getCatId, categoryDTO -> categoryDTO));
        List<CategoryDTO> resultList=new ArrayList<>();
        //获取类型关系
        categoryEntities.forEach(categoryEntity -> {
            CategoryDTO curCategoryDTO = categoryDTOMap.get(categoryEntity.getCatId());
            if (categoryEntity.getParentCid()==0){
                //一级目录
                resultList.add(curCategoryDTO);
            }else {
                //非一级目录
                CategoryDTO categoryDTO = categoryDTOMap.get(categoryEntity.getParentCid());
                if (CollectionUtils.isEmpty(categoryDTO.getChildrenList())){
                    ArrayList<CategoryDTO> childrenList = new ArrayList<>();
                    categoryDTO.setChildrenList(childrenList);
                }
                categoryDTO.getChildrenList().add(curCategoryDTO);
            }
        });
        sortTree(resultList);
        return resultList;
    }

    /**
     * 种类排序
     * @param resultList resultList
     */
    private void sortTree(List<CategoryDTO> resultList) {
        try {
            if (CollectionUtils.isEmpty(resultList)||resultList.size()==1){
                return;
            }
            resultList.sort(Comparator.comparingInt(CategoryDTO::getSort));
            resultList.forEach(categoryDTO -> {
                if (CollectionUtils.isNotEmpty(categoryDTO.getChildrenList())){
                    sortTree(categoryDTO.getChildrenList());
                }
            });
        } catch (Exception e) {
            System.out.println(Collections.singletonList(resultList));
            log.error("sortTree error",e);
        }
    }

}