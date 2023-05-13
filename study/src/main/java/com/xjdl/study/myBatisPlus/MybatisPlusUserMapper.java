package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//BaseMapper实现了许多单表增删改查
public interface MybatisPlusUserMapper extends BaseMapper<MybatisPlusUser> {
	public List<MybatisPlusUser> selectAll();
}
