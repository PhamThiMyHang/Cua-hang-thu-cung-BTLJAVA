package com.cuahangthucung.dto.use.giohang;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GioHangDTO {

    private String maGioHang;
    private String maSP;
    private String tenSP;           // Làm phẳng từ SanPham
    private Integer soLuong;
    private BigDecimal donGia;      // Giá bán từ SanPham
    private BigDecimal thanhTien;   // = soLuong * donGia

    // Thông tin User
    private String maUser;
    private String tenUser;

    // Có thể thêm nếu cần
    private String hinhAnhSP;
}