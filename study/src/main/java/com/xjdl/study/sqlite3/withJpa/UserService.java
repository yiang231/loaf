package com.xjdl.study.sqlite3.withJpa;

import com.xjdl.study.sqlite3.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userServiceImpl")
public class UserService {
	@Resource
	private UserRepository userRepository;

	public User getUserByID(Long id) {
		return userRepository.findById(id).get();
	}

	public User getByName(String name) {
		return userRepository.findByName(name);
	}

	public Page<User> findPage() {
		Pageable pageable = PageRequest.of(0, 10);
		return userRepository.findAll(pageable);
	}

	public Page<User> find(Long maxId) {
		Pageable pageable = PageRequest.of(0, 10);
		return userRepository.findMore(maxId, pageable);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public User update(Long id, String name) {
		User user = userRepository.findById(id).get();
		user.setName(name + "_update");
		return userRepository.save(user);
	}

	public int updateById(String name, Long id) {
		return userRepository.updateById(name, id);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}
