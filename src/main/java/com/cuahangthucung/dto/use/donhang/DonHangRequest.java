package com.cuahangthucung.dto.use.donhang;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class DonHangRequest {
    private String maDH;

    @NotBlank(message = "Mã khách hàng không được để trống")
    private String maKH;

    @NotBlank(message = "Mã nhân viên không được để trống")
    private String maNV;

    @NotNull(message = "Ngày tạo không được để trống")
    private LocalDate ngayTao;

    @NotBlank(message = "Trạng thái đơn hàng không được để trống")
    private String trangThai;
}