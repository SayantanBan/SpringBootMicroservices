package com.cgi.jwtAuthentication.jwtAuthentication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.jwtAuthentication.jwtAuthentication.domain.User;
import com.cgi.jwtAuthentication.jwtAuthentication.repository.UserRepository;
import com.cgi.jwtAuthentication.jwtAuthentication.service.GenericService;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

}
