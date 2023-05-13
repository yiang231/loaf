package com.xjdl.study.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
	@Resource
	private UserRepository userRepository;

	public JpaUser getUserByID(Long id) {
		return userRepository.findById(id).get();
	}

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

	public JpaUser save(JpaUser jpaUser) {
		return userRepository.save(jpaUser);
	}

	public JpaUser update(Long id, String name) {
		JpaUser jpaUser = userRepository.findById(id).get();
		jpaUser.setName(name + "_update");
		return userRepository.save(jpaUser);
	}

	public int updateById(String name, Long id) {
		return userRepository.updateById(name, id);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}
