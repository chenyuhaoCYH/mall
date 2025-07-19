package com.cyh.mall.member.dao;

import com.cyh.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author chenyuhao
 * @email chenyuhao@gmail.com
 * @date 2025-07-19 15:45:41
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
