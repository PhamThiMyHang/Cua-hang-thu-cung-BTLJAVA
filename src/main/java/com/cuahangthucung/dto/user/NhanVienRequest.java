package com.cuahangthucung.dto.user;

import com.cuahangthucung.entity.user.enums.ChucVu;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NhanVienRequest {
    private Integer maNV; // null khi tạo mới

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String tenNV;

    private String sdt;
    private String diaChi;

    @NotNull(message = "Chức vụ không được để trống")
    private ChucVu chucVu;

    private Integer userID;
}