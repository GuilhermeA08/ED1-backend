package com.ed1.article.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ed1.article.exception.BadRequestException;
import com.ed1.article.model.User;
import com.ed1.article.repository.UserRepository;
import com.ed1.article.structures.List.DoubleLinkedList;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public DoubleLinkedList<User> findAll() {
		DoubleLinkedList<User> userList = new DoubleLinkedList<>();
		userList.addAll(userRepository.findAll());
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
		List<String> errors = new ArrayList<>();
		
		User verifyEmail = userRepository.findByEmail(userDto.getEmail());
		if(verifyEmail != null) {
			errors.add("Email already registered. Try again with another email");
		}
		
		if(errors.size() != 0) {
			throw new BadRequestException(errors);
		}
		
		// Criptografa a senha
		userDto.setPassword(encoder.encode(userDto.getPassword()));
		
		User userDb = userRepository.save(userDto);
		return userDb;
	}
	
	@Transactional
	public User update(Long id, User userDto) {
		Optional<User> userOpt = userRepository.findById(id);
		User userEntity = userOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		userEntity.copy(userDto);
		
		// Criptografa a senha
		userDto.setPassword(encoder.encode(userDto.getPassword()));	
		
		return userRepository.save(userEntity);
	}
	
	@Transactional
	public void deleteUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		User userEntity = userOpt.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		
		userRepository.delete(userEntity);
	}
}
