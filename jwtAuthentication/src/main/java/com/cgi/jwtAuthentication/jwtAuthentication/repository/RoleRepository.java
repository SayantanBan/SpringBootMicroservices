package com.cgi.jwtAuthentication.jwtAuthentication.repository;

import org.springframework.data.repository.CrudRepository;

import com.cgi.jwtAuthentication.jwtAuthentication.domain.Role;

interface RoleRepository extends CrudRepository<Role, Long> {
}
