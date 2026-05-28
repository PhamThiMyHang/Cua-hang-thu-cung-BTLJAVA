package com.cuahangthucung.dto.use.phieunhapkho;
/*
import lombok.Data;
import java.time.LocalDate;

@Data
public class PhieuNhapKhoRequest {
    private String maPhieu; // Để trống khi tạo mới (tự sinh)
    private String maSP;
    private LocalDate ngayNhap;
    private int soLuong;
}*/

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PhieuNhapKhoRequest {

    @NotBlank(message = "Mã phiếu không được để trống")
    private String maPhieu;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;

    @NotNull(message = "Ngày nhập không được để trống")
    private LocalDate ngayNhap;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Đơn giá nhập không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá nhập phải lớn hơn hoặc bằng 0")
    private BigDecimal donGia;   // Giá nhập (nếu có)
}
