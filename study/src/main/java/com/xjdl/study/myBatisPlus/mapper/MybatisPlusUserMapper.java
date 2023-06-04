package com.xjdl.study.myBatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjdl.study.myBatisPlus.MybatisPlusUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//BaseMapper实现了许多单表增删改查
public interface MybatisPlusUserMapper extends BaseMapper<MybatisPlusUser> {
	List<MybatisPlusUser> selectAll();
}
