package com.ed1.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ed1.article.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User AS u WHERE u.email=:email")
	public User findByEmail(@Param("email") String email);
}
