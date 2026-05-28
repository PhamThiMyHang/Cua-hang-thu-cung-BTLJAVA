package com.cuahangthucung.dto.use.chitietdonhang;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangDTO {

    private String maDH;
    private String maSP;
    private String tenSP;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    // Thông tin đơn hàng
    private LocalDate ngayTao;
    private String trangThaiDonHang;
}