package com.cuahangthucung.dto.use.chitietdonhang;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChiTietDonHangRequest {

    @NotBlank(message = "Mã đơn hàng không được để trống")
    private String maDH;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được để trống")
    @Positive(message = "Đơn giá phải lớn hơn 0")
    private BigDecimal donGia;
}