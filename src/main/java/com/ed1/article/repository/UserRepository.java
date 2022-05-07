package com.ed1.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ed1.article.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
