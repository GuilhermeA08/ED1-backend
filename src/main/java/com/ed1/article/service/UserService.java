package com.ed1.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ed1.article.model.User;
import com.ed1.article.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<User> findAll() {
		List<User> userList = userRepository.findAll();
		return userList;
	}
	
	@Transactional
	public User save(User userDto) {
		User userDb = userRepository.save(userDto);
		return userDb;
	}
}
