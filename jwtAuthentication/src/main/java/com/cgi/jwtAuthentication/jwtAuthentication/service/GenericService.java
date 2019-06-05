package com.cgi.jwtAuthentication.jwtAuthentication.service;

import java.util.List;

import com.cgi.jwtAuthentication.jwtAuthentication.domain.User;

public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

}
