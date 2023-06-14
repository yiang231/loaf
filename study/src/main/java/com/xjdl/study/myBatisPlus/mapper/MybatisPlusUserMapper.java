package com.xjdl.study.myBatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjdl.study.myBatisPlus.MybatisPlusUser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//BaseMapper实现了许多单表增删改查
public interface MybatisPlusUserMapper extends BaseMapper<MybatisPlusUser> {
	List<MybatisPlusUser> selectAll();

	// 必须加 @Param 注解
	MybatisPlusUser selectByName(@Param("searchText") String searchText);
}
