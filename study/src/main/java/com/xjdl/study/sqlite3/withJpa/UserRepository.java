package com.xjdl.study.sqlite3.withJpa;

import com.xjdl.study.sqlite3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //默认提供了Optional<User> findById(Long id);
    User findByName(String name);

    // @Param 代替参数占位符，  hql或者sql里就用  :firstname替换   方法里的参数顺序可以打乱
    @Query("select u from User u where u.id <= ?1")
    Page<User> findMore(Long maxId, Pageable pageable);

    @Modifying //如果是更新或者删除操作，方法上面要加@Modifying      默认开启的事务只是可读的，更新操作加入@Modifying 就会关闭可读
    @Transactional
    @Query("update User u set u.name = ?1 where u.id = ?2")
    int updateById(String name, Long id);
}
