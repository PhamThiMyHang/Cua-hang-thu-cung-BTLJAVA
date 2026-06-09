package com.cuahangthucung.service.user.service;

import com.cuahangthucung.dto.user.User.UserDTO;
import com.cuahangthucung.dto.user.User.UserRequest;
import com.cuahangthucung.dto.user.User.UserSearchRequest;
import com.cuahangthucung.dto.user.User.UserSummaryDTO;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, Integer> {
    // Thêm dòng này để AuthController có thể gọi được
    Optional<User> findByGmail(String gmail);
    List<UserDTO> findByRole(String roleName);

    List<UserDTO> search(UserSearchRequest request);

    // Phiên bản có phân trang (dùng cho Controller)
    Page<UserDTO> search(UserSearchRequest request, Pageable pageable);
    UserDTO saveRequest(UserRequest request);
    UserDTO findByIdDTO(Integer id);
    List<UserDTO> findAllDTO();
    UserSummaryDTO getSummary();
    boolean existsByUsername(String username);
    boolean existsByGmail(String gmail); // Bổ sung check trùng Gmail

    /*25/05/2026*/
    UserDTO linkNhanVien(Integer userID, Integer maNV);
    UserDTO linkKhachHang(Integer userID, Integer maKH);

    // UserService.java
    UserDTO registerCustomer(UserRequest request);

    UserDTO changeRole(Integer userID, String roleName);

    UserDTO changeUsername( Integer userID, String username );

    UserDTO toggleStatus(Integer userID);
}