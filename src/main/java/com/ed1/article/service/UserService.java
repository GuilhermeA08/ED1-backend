package com.ed1.article.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

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
	
	@Transactional(readOnly = true)
	public User findById(Long id) {
		Optional<User> opt = userRepository.findById(id);
		User user = opt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return user;
	}
	
	@Transactional
	public User save(User userDto) {
		User userDb = userRepository.save(userDto);
		return userDb;
	}
	
	@Transactional
	public User update(Long id, User userDto) {
		Optional<User> userOpt = userRepository.findById(id);
		User userEntity = userOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		userEntity.copy(userDto);
		
		return userRepository.save(userEntity);
	}
	
	@Transactional
	public void deleteUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		User userEntity = userOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		userRepository.delete(userEntity);
	}
}
