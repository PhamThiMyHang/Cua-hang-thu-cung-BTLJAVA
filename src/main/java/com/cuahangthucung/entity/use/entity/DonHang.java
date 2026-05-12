package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.use.enums.TrangThai;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class DonHang {

    @Id
    private String maDH;

    // MaKH và MaNV chưa có bảng riêng nên để String
    private String maKH;
    private String maNV;

    private LocalDate ngayTao;

    private double tongTien;

    @Enumerated(EnumType.STRING)
    private TrangThai trangThai = TrangThai.PENDING;
}