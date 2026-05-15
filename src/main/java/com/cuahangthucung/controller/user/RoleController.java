package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.service.user.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * ROLE CONTROLLER
 * =============================================
 * Quản lý Vai trò (Role) trong hệ thống
 * Hỗ trợ phân quyền: ADMIN, STAFF, KTV, CUSTOMER
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController extends BaseController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * LẤY TẤT CẢ VAI TRÒ (có hỗ trợ phân trang)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(
            @PageableDefault(size = 20, sort = "roleID", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Role> roles = roleService.findAll(pageable);  // Sử dụng phương thức từ BaseService
        return resSuccess(roles, "Lấy danh sách vai trò thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return roleService.findById(id)
                .map(role -> resSuccess(role, "Tìm thấy vai trò"))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Tìm vai trò theo tên (ADMIN, STAFF, KTV, CUSTOMER...)
     */
    @GetMapping("/name/{roleName}")
    public ResponseEntity<Map<String, Object>> getByRoleName(@PathVariable String roleName) {
        return roleService.findByRoleName(roleName)
                .map(role -> resSuccess(role, "Tìm thấy vai trò: " + roleName))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Role role) {
        Role saved = roleService.save(role);
        return resCreated(saved, "Thêm vai trò mới thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return resSuccess(null, "Xóa vai trò thành công");
    }
}