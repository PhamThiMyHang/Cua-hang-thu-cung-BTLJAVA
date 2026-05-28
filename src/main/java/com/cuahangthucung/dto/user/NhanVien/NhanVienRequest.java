package com.cuahangthucung.dto.user.NhanVien;

import com.cuahangthucung.entity.user.enums.ChucVu;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NhanVienRequest {
    private Integer maNV; // null khi tạo mới

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String tenNV;

    @NotBlank(message = "Gmail nhân viên không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Hệ thống chỉ chấp nhận tài khoản @gmail.com")
    private String gmail; // Bổ sung trường Gmail bắt buộc

    private String sdt;
    private String diaChi;

    @NotNull(message = "Chức vụ không được để trống")
    private ChucVu chucVu;

    private Integer userID;
}