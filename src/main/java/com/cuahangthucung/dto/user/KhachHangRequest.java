package com.cuahangthucung.dto.user;

import com.cuahangthucung.entity.user.enums.LoaiKH;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KhachHangRequest {
    private Integer maKH;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String tenKH;

    private String sdt;
    private String diaChi;

    @NotNull(message = "Loại khách hàng không được để trống")
    private LoaiKH loaiKH = LoaiKH.THUONG;

    private Integer userID;
}