package com.api.springsecurity.service;

import com.api.springsecurity.persistence.entity.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
