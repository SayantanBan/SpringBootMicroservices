package com.cgi.jwtAuthentication.jwtAuthentication.repository;

import org.springframework.data.repository.CrudRepository;

import com.cgi.jwtAuthentication.jwtAuthentication.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
