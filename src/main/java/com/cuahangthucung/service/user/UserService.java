package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.base.BaseService;

import java.util.Optional;

public interface UserService extends BaseService<User, Integer> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    String generateNextUsername();
}