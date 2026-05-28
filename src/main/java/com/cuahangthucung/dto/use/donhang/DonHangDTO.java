package com.cuahangthucung.dto.use.donhang;


import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonHangDTO {
    private String maDH;
    private String maKH;
    private String tenKH;
    private String maNV;
    private String tenNV;
    private LocalDate ngayTao;
    private BigDecimal tongTien;
    private String trangThai;

    private List<ChiTietDonHangDTO> chiTietDonHang;
}