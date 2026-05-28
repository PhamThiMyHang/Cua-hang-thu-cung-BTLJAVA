package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.User.UserDTO;
import com.cuahangthucung.dto.user.User.UserRequest;
import com.cuahangthucung.dto.user.User.UserSearchRequest;
import com.cuahangthucung.dto.user.User.UserSummaryDTO;
import com.cuahangthucung.service.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * USER CONTROLLER
 * =============================================
 * Quản lý tài khoản người dùng hệ thống
 * Liên kết chính: Role (Many-to-Many), NhanVien, KhachHang, LichSuDangNhap
 */
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/users/search?username=admin&status=ACTIVE&roleName=STAFF&keyword=Nguyễn
     *        &sortBy=username&sortDir=asc&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            UserSearchRequest request,
            @PageableDefault(size = 10, sort = "userID", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<UserDTO> resultPage = userService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm người dùng thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(userService.findAllDTO(), "Lấy toàn bộ danh sách người dùng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(userService.findByIdDTO(id), "Lấy thông tin người dùng thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest request) {
        UserDTO saved = userService.saveRequest(request);
        return resCreated(saved, "Tạo tài khoản người dùng thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {
        request.setUserID(id);
        UserDTO updated = userService.saveRequest(request);
        return resSuccess(updated, "Cập nhật người dùng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return resSuccess(null, "Xóa người dùng thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        UserSummaryDTO summary = userService.getSummary();
        return resSuccess(summary, "Lấy thống kê người dùng thành công");
    }

    /*25/05/2026*/
    @PostMapping("/{userID}/link-nhanvien/{maNV}")
    public ResponseEntity<Map<String, Object>> linkNhanVien(
            @PathVariable Integer userID,
            @PathVariable Integer maNV) {

        UserDTO updated = userService.linkNhanVien(userID, maNV);
        return resSuccess(updated, "Liên kết User với Nhân viên thành công");
    }

    @PostMapping("/{userID}/link-khachhang/{maKH}")
    public ResponseEntity<Map<String, Object>> linkKhachHang(
            @PathVariable Integer userID,
            @PathVariable Integer maKH) {

        UserDTO updated = userService.linkKhachHang(userID, maKH);
        return resSuccess(updated, "Liên kết User với Khách hàng thành công");
    }
}