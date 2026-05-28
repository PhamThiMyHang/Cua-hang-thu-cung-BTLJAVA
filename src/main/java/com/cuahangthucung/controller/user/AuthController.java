package com.cuahangthucung.controller.user; // Đặt cùng package với UserController của bạn

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.User.UserDTO;
import com.cuahangthucung.dto.user.User.UserRequest;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.entity.user.enums.UserStatus;
import com.cuahangthucung.service.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*") // Fix lỗi CORS khi Next.js gọi sang Spring Boot
public class AuthController extends BaseController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    // Tiêm cả bộ mã hóa từ SecurityConfig vào đây qua Constructor
    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String gmailInput = loginRequest.get("gmail");
        String passwordInput = loginRequest.get("password");

        // 1. Tìm kiếm User theo Gmail
        Optional<User> userOpt = userService.findByGmail(gmailInput);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Tài khoản Gmail không tồn tại trên hệ thống!"));
        }

        User user = userOpt.get();

        // 2. Kiểm tra trạng thái hoạt động (ACTIVE / INACTIVE)
        if (UserStatus.INACTIVE.equals(user.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "Tài khoản này đã bị khóa!"));
        }

        // 3. Khớp mật khẩu (Mật khẩu thô hoặc chuỗi Bcrypt $2a$)
        boolean isPasswordMatch = false;
        String dbPassword = user.getPassword();

        if (dbPassword != null && dbPassword.startsWith("$2a$")) {
            isPasswordMatch = passwordEncoder.matches(passwordInput, dbPassword);
        } else {
            isPasswordMatch = passwordInput.equals(dbPassword);
        }

        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Mật khẩu không chính xác!"));
        }

        // 4. Nếu đúng mật khẩu -> Sử dụng hàm convertToDTO có sẵn mà bạn viết ở UserServiceImpl
        // Bằng cách ép kiểu hoặc gọi qua hàm có sẵn để sinh ra dữ liệu liên kết Nhân viên / Khách hàng đầy đủ
        UserDTO userDTO = userService.findByIdDTO(user.getUserID());

        // Làm sạch dữ liệu Role: Xóa kí tự xuống dòng ẩn \r và đưa về dạng chữ thường
        if (userDTO.getRoles() != null) {
            userDTO.setRoles(userDTO.getRoles().stream()
                    .map(role -> role.replace("\r", "").toLowerCase())
                    .collect(Collectors.toSet()));
        }

        // Kế thừa cấu trúc trả về resSuccess từ BaseController của bạn
        return resSuccess(userDTO, "Đăng nhập thành công!");
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserRequest request) {
        try {
            // Gọi hàm vừa tạo trong UserService
            UserDTO userDTO = userService.registerCustomer(request);
            return resCreated(userDTO, "Đăng ký thành công!");
        } catch (RuntimeException e) {
            // Bắt lỗi nếu email đã tồn tại hoặc thiếu Role
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}