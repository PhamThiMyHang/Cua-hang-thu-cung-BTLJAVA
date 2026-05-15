package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * USER CONTROLLER
 * =============================================
 * Quản lý tài khoản người dùng hệ thống
 * Liên kết chính: Role (Many-to-Many), NhanVien, KhachHang, LichSuDangNhap
 * Đây là bảng core của module quản lý người dùng.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Tìm kiếm và lọc người dùng
     * Hỗ trợ: username, status, roleName, keyword (tìm theo tên NV hoặc KH)
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(UserSearchRequest request) {
        var result = userService.search(request);
        return resSuccess(result, "Tìm kiếm người dùng thành công");
    }

    /**
     * Lấy toàn bộ danh sách người dùng trong hệ thống
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(userService.findAllDTO(), "Lấy toàn bộ danh sách người dùng thành công");
    }

    /**
     * Lấy thông tin chi tiết một User theo ID
     * Trả về thông tin roles và thông tin liên kết NhanVien/KhachHang (nếu có)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(userService.findByIdDTO(id), "Lấy thông tin người dùng thành công");
    }

    /**
     * Tạo mới tài khoản người dùng
     * Có thể gán nhiều Role khi tạo
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest request) {
        UserDTO saved = userService.saveRequest(request);
        return resCreated(saved, "Tạo tài khoản người dùng thành công");
    }

    /**
     * Cập nhật thông tin tài khoản
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {
        request.setUserID(id);
        UserDTO updated = userService.saveRequest(request);
        return resSuccess(updated, "Cập nhật người dùng thành công");
    }

    /**
     * Xóa tài khoản người dùng
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return resSuccess(null, "Xóa người dùng thành công");
    }

    /**
     * Thống kê tổng quan người dùng cho Dashboard
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        UserSummaryDTO summary = userService.getSummary();
        return resSuccess(summary, "Lấy thống kê người dùng thành công");
    }
}