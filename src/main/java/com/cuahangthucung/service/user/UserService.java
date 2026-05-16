package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends BaseService<User, Integer> {

    List<UserDTO> search(UserSearchRequest request);
    
    // Phiên bản có phân trang (dùng cho Controller)
    Page<UserDTO> search(UserSearchRequest request, Pageable pageable);

    UserDTO saveRequest(UserRequest request);
    UserDTO findByIdDTO(Integer id);
    List<UserDTO> findAllDTO();
    UserSummaryDTO getSummary();
    boolean existsByUsername(String username);
}