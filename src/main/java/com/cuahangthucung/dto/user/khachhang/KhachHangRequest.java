package com.cuahangthucung.dto.user.khachhang;

import com.cuahangthucung.entity.user.enums.LoaiKH;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KhachHangRequest {
    private Integer maKH;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String tenKH;

    @NotBlank(message = "Gmail khách hàng không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Hệ thống chỉ chấp nhận tài khoản @gmail.com")
    private String gmail; // Bổ sung trường Gmail bắt buộc

    private String sdt;
    private String diaChi;

    @NotNull(message = "Loại khách hàng không được để trống")
    private LoaiKH loaiKH = LoaiKH.THUONG;

    private Integer userID;
}