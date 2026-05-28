package com.cuahangthucung.dto.use.giohang;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GioHangRequest {

    private String maGioHang;   // Có thể null khi thêm mới

    @NotBlank(message = "Mã người dùng không được để trống")
    private String maUser;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
    private Integer soLuong;
}