package com.api.springsecurity.service;

import com.api.springsecurity.dto.SaveUser;
import com.api.springsecurity.persistence.entity.security.User;

import java.util.Optional;

public interface UserService {
    User registrOneCustomer(SaveUser newUser);

    Optional<User> findOneByUsername(String username);
}
