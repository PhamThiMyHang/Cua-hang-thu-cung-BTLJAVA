package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(userService.findAll(), "Lấy danh sách người dùng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(user -> resSuccess(user, "Tìm thấy người dùng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> resSuccess(user, "Tìm thấy người dùng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody User user) {
        User saved = userService.save(user);
        return resCreated(saved, "Tạo tài khoản thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return resSuccess(updated, "Cập nhật người dùng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return resSuccess(null, "Xóa người dùng thành công");
    }
}