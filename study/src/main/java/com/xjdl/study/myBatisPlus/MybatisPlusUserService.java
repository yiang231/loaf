package com.xjdl.study.myBatisPlus;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MybatisPlusUserService extends IService<MybatisPlusUser> {
	List<MybatisPlusUser> selectAll();
    MybatisPlusUser selectById(String id);
    void insert(MybatisPlusUser user);
    void deleteById(Long id);
	MybatisPlusUser query(String searchText);
}
