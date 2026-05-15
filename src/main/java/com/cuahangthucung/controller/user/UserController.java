package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(UserSearchRequest request) {
        List<UserDTO> list = userService.search(request);
        return resSuccess(list, "Tìm kiếm user thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<UserDTO> list = userService.findAllDTO();
        return resSuccess(list, "Lấy danh sách user thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        UserDTO dto = userService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy user");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody UserRequest request) {
        UserDTO saved = userService.saveRequest(request);
        return resCreated(saved, "Tạo user thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody UserRequest request) {
        request.setUserID(id);
        UserDTO updated = userService.saveRequest(request);
        return resSuccess(updated, "Cập nhật user thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return resSuccess(null, "Xóa user thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        UserSummaryDTO summary = userService.getSummary();
        return resSuccess(summary, "Lấy thống kê user thành công");
    }
}