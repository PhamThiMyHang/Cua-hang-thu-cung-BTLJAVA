package com.cuahangthucung.dto.use.phieuxuatkho;
/*
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PhieuXuatKhoRequest {

    @NotBlank(message = "Mã phiếu không được để trống")
    private String maPhieu;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;

    @NotNull
    private LocalDate ngayXuat;

    @NotNull
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    private Double donGia;

    @NotBlank
    private String maNV;

    private String noiNhan;
    private String ghiChu;
}*/


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PhieuXuatKhoRequest {

    @NotBlank(message = "Mã phiếu không được để trống")
    private String maPhieu;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;

    @NotNull(message = "Ngày xuất không được để trống")
    private LocalDate ngayXuat;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Đơn giá xuất không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá xuất phải lớn hơn hoặc bằng 0")
    private BigDecimal donGia;

    @NotBlank(message = "Mã nhân viên thực hiện không được để trống")
    private String maNV;

    private String noiNhan;
    private String ghiChu;
}