package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.service.user.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController extends BaseController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(roleService.findAll(), "Lấy danh sách vai trò thành công");
    }

    @GetMapping("/name/{roleName}")
    public ResponseEntity<Map<String, Object>> getByRoleName(@PathVariable String roleName) {
        return roleService.findByRoleName(roleName)
                .map(role -> resSuccess(role, "Tìm thấy vai trò"))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Role role) {
        return resCreated(roleService.save(role), "Thêm vai trò thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return resSuccess(null, "Xóa vai trò thành công");
    }
}