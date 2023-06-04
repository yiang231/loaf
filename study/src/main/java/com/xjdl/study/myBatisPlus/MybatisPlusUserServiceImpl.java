package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjdl.study.myBatisPlus.mapper.MybatisPlusUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MybatisPlusUserServiceImpl extends ServiceImpl<MybatisPlusUserMapper, MybatisPlusUser> implements MybatisPlusUserService {
	@Resource
	private MybatisPlusUserMapper mybatisPlusUserMapper;

	@Override
	public List<MybatisPlusUser> selectAll() {
		return mybatisPlusUserMapper.selectAll();
	}
}
