package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends BaseService<User, Integer> {

    // Tìm kiếm có phân trang (Khuyến nghị dùng)
    Page<UserDTO> search(UserSearchRequest request, Pageable pageable);

    // Giữ lại nếu cần dùng List (không phân trang)
    List<UserDTO> search(UserSearchRequest request);

    UserDTO saveRequest(UserRequest request);
    UserDTO findByIdDTO(Integer id);
    List<UserDTO> findAllDTO();
    UserDTO convertToDTO(User entity);

    // Thống kê
    UserSummaryDTO getSummary();
}