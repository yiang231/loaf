package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjdl.study.caffeine.CaffeineController;
import com.xjdl.study.caffeine.annotation.MyCacheManager;
import com.xjdl.study.myBatisPlus.mapper.MybatisPlusUserMapper;
import com.xjdl.study.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@MyCacheManager
@Slf4j
public class MybatisPlusUserServiceImpl extends ServiceImpl<MybatisPlusUserMapper, MybatisPlusUser> implements MybatisPlusUserService {
	@Resource
	private MybatisPlusUserMapper mybatisPlusUserMapper;

	/**
	 * Caching 复合注解
	 * 定义多种缓存行为
	 * <p>
	 *
	 * @see CaffeineController
	 */
	@Caching(
			cacheable = {
					@Cacheable(cacheNames = "mpUser")
			}
	)
	@Override
	public List<MybatisPlusUser> selectAll() {
		return mybatisPlusUserMapper.selectAll();
	}

	/**
	 * Cacheable
	 * <p>
	 * 将运行结果进行返回缓存，使用参数id作为缓存的key
	 * <p>
	 * 指定id不为空时进行数据缓存作为附加条件
	 */
	@Cacheable(cacheNames = "mpUser", key = "#id", unless = "#id == null")
	@Override
	public MybatisPlusUser selectById(String id) {
		MybatisPlusUser mybatisPlusUser = baseMapper.selectById(id);
		return mybatisPlusUser;
	}

	/**
	 * CachePut
	 * <p>
	 * 向缓存中添加数据，执行前不会检查缓存是否存在，每次都会执行方法，并且写入缓存数据，相当于使用的是写模式中的双写模式
	 * <p>
	 * 指定参数user的id不为空时进行缓存作为附加条件
	 */
	@CachePut(cacheNames = "mpUser", key = "#user.id", condition = "#user.id != null")
	@Override
	public void insert(MybatisPlusUser user) {
		baseMapper.insert(user);
	}

	/**
	 * CacheEvict
	 * <p>
	 * 从缓存中移除数据，相当于使用的是写模式中的失效模式
	 */
	@CacheEvict(cacheNames = "mpUser", key = "#id")
	@Override
	public void deleteById(Long id) {
		baseMapper.deleteById(id);
	}

	@Cacheable(cacheNames = "mpUser", key = "#searchText")
	@Override
	public MybatisPlusUser query(String searchText) {
		if (MyUtils.isNumber(searchText)) {
			return this.selectById(searchText);
		}
		return mybatisPlusUserMapper.selectByName(searchText);
	}
}
