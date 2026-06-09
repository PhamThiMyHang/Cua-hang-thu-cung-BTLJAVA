package com.cuahangthucung.dto.use.sanpham;
/*
import lombok.Data;

@Data
public class SanPhamRequest {
    private String maSP; // Để trống khi thêm mới
    private String tenSP;
    private double gia;
    private int soLuong;
    private String hanSuDung;
    private String viTri;

    // Chỉ nhận ID để Service tự truy vấn Object NhaCungCap
    private String maNCC;
}
*/

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class SanPhamRequest {
    private String maSP;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSP;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private BigDecimal gia;

    private Integer soLuong;
    private LocalDate hanSuDung;

    private String viTri;
    @NotBlank(message = "Mã nhà cung cấp không được để trống")
    private String maNCC;
    private String urlImg;
}