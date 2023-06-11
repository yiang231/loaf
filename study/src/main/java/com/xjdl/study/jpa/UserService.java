package com.xjdl.study.jpa;

import com.xjdl.study.config.caffeine.MyCacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@MyCacheManager
public class UserService {
    @Resource
    private UserRepository userRepository;

    @Cacheable(cacheNames = "jpaUser", key = "#id")
    public JpaUser getUserByID(Long id) {
        return userRepository.findById(id).get();
    }

    @Cacheable(cacheNames = "jpaUser", key = "#result.id")
    public JpaUser getByName(String name) {
        return userRepository.findByName(name);
    }

    public Page<JpaUser> findPage() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findAll(pageable);
    }

    public Page<JpaUser> find(Long maxId) {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.findMore(maxId, pageable);
    }

    @CachePut(cacheNames = "jpaUser", key = "#result.id")
    public JpaUser save(JpaUser jpaUser) {
        return userRepository.save(jpaUser);
    }

    @CachePut(cacheNames = "jpaUser", key = "#id")
    public JpaUser update(Long id, String name) {
        JpaUser jpaUser = userRepository.findById(id).get();
        jpaUser.setName(name + "_update");
        return userRepository.save(jpaUser);
    }

    @CachePut(cacheNames = "jpaUser", key = "#id")
    public int updateById(String name, Long id) {
        return userRepository.updateById(name, id);
    }

    @CacheEvict(cacheNames = "jpaUser", key = "#id")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
