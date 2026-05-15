package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.service.user.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * ROLE CONTROLLER
 * =============================================
 * Quản lý Vai trò (Role) trong hệ thống
 * Hỗ trợ phân quyền: ADMIN, STAFF, KTV, CUSTOMER
 * Đây là bảng quan trọng cho hệ thống phân quyền
 */
@RestController
@RequestMapping("/api/roles")

public class RoleController extends BaseController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Lấy tất cả các vai trò hiện có trong hệ thống
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        var roles = roleService.findAll();
        return resSuccess(roles, "Lấy danh sách vai trò thành công");
    }

    /**
     * Lấy thông tin một vai trò theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return roleService.findById(id)
                .map(role -> resSuccess(role, "Tìm thấy vai trò"))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Tìm vai trò theo tên (ví dụ: ADMIN, STAFF, KTV...)
     */
    @GetMapping("/name/{roleName}")
    public ResponseEntity<Map<String, Object>> getByRoleName(@PathVariable String roleName) {
        return roleService.findByRoleName(roleName)
                .map(role -> resSuccess(role, "Tìm thấy vai trò: " + roleName))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Thêm mới một vai trò
     * Thường chỉ dùng bởi Admin
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Role role) {
        Role saved = roleService.save(role);
        return resCreated(saved, "Thêm vai trò mới thành công");
    }

    /**
     * Xóa một vai trò
     * Cần cẩn thận vì có thể ảnh hưởng đến User
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return resSuccess(null, "Xóa vai trò thành công");
    }
}