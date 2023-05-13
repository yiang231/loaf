package com.xjdl.study.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<JpaUser, Long> {

	//默认提供了Optional<MybatisPlusUser> findById(Long id);
	JpaUser findByName(String name);

	// @Param 代替参数占位符，  hql或者sql里就用  :firstname替换   方法里的参数顺序可以打乱
	@Query("select u from JpaUser  u where u.id <= ?1")
	Page<JpaUser> findMore(Long maxId, Pageable pageable);

	@Modifying //如果是更新或者删除操作，方法上面要加@Modifying      默认开启的事务只是可读的，更新操作加入@Modifying 就会关闭可读
	@Transactional
	@Query("update JpaUser u set u.name = ?1 where u.id = ?2")
	int updateById(String name, Long id);
}
