package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<User, Integer> {

    List<UserDTO> search(UserSearchRequest request);
    UserDTO saveRequest(UserRequest request);
    UserDTO findByIdDTO(Integer id);
    List<UserDTO> findAllDTO();
    UserSummaryDTO getSummary();
    boolean existsByUsername(String username);
}