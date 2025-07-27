package com.cyh.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyh.mall.common.constant.RequestConstant;
import com.cyh.mall.common.utils.R;
import com.cyh.mall.common.constant.ResponseConstant;
import com.cyh.mall.product.common.response.CategoryDTO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.BatchResult;
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
            if (categoryEntity.getParentCid()==null||categoryEntity.getParentCid()==0){
                //一级目录
                resultList.add(curCategoryDTO);
            }else {
                //非一级目录
                CategoryDTO categoryDTO = categoryDTOMap.get(categoryEntity.getParentCid());
                if (CollectionUtils.isEmpty(categoryDTO.getChildrens())){
                    ArrayList<CategoryDTO> childrenList = new ArrayList<>();
                    categoryDTO.setChildrens(childrenList);
                }
                categoryDTO.getChildrens().add(curCategoryDTO);
            }
        });
        sortTree(resultList);
        return resultList;
    }

    /**
     * 删除种类-逻辑删除
     * @param deleteCatIds deleteCatIds
     */
    @Override
    public R removeCategoryByIds(List<String> deleteCatIds) {
        try {
            if (CollectionUtils.isEmpty(deleteCatIds)){
                return R.error(ResponseConstant.Delete.NULL_LIST);
            }
            List<Long> catIds = deleteCatIds.stream().map(Long::parseLong).collect(Collectors.toList());
            List<CategoryEntity> categoryEntities = baseMapper.selectList(new LambdaQueryWrapper<CategoryEntity>()
                    .eq(CategoryEntity::getShowStatus,1)
                    .in(CategoryEntity::getParentCid, catIds)
                    .notIn(CategoryEntity::getCatId, catIds));
            if (CollectionUtils.isNotEmpty(categoryEntities)){

                 return R.error(ResponseConstant.Delete.NOT_DELETE);
            }
            //todo 业务逻辑判断是否还在使用
            baseMapper.delete(new QueryWrapper<CategoryEntity>().in("cat_id",catIds));
            return R.ok(ResponseConstant.Delete.SUCCESS);
        } catch (Exception e) {
            log.error("removeCategoryByIds error:",e);
        }
        return R.error(ResponseConstant.Delete.FAIL);
    }

    @Override
    public R saveCategory(CategoryEntity category) {
        if (category==null||category.getParentCid()==null|| StringUtils.isBlank(category.getName())){
            return R.error(RequestConstant.PARAM_ERROR);
        }
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getCatId, category.getParentCid()));
        if (CollectionUtils.isEmpty(categoryEntities)){
            return R.error(ResponseConstant.Save.Category.PARENT_CATEGORY_NOT_EXIST);
        }
        int insert = baseMapper.insert(category);
        return insert>0?R.ok():R.error(ResponseConstant.Save.FAIL);
    }

    @Override
    public boolean updateBatch(List<CategoryEntity> categoryList) {
        if (CollectionUtils.isEmpty(categoryList)){
            return false;
        }
        List<BatchResult> batchResults = baseMapper.updateById(categoryList);
        return true;
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
                if (CollectionUtils.isNotEmpty(categoryDTO.getChildrens())){
                    sortTree(categoryDTO.getChildrens());
                }
            });
        } catch (Exception e) {
            System.out.println(Collections.singletonList(resultList));
            log.error("sortTree error",e);
        }
    }

}