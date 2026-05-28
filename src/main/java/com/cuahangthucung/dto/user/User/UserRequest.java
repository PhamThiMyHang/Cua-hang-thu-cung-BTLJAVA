package com.cuahangthucung.dto.user.User;

import com.cuahangthucung.entity.user.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private Integer userID; // Dùng khi update

    @NotBlank(message = "Tên người dùng không được để trống")
    private String username;

    @NotBlank(message = "Gmail không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Hệ thống chỉ chấp nhận tài khoản @gmail.com")
    private String gmail;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải từ 10 đến 11 chữ số")
    private String soDienThoai;

    private String diaChi; // Không bắt buộc

    private UserStatus status = UserStatus.ACTIVE;

    private Set<String> roleNames; // Backend sẽ tự gán mặc định là CUSTOMER
}