package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.use.enums.TrangThai;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class LichHen {

    @Id
    private String maLich;

    // MaKH, MaPet, MaNV chưa có bảng riêng nên để String
    private String maKH;
    private String maPet;
    private String maNV;

    @ManyToOne
    @JoinColumn(name = "maDV")
    private DichVu dichVu;

    // Dùng LocalDateTime vì SQL dùng kiểu DATETIME
    private LocalDateTime thoiGian;

    @Enumerated(EnumType.STRING)
    private TrangThai trangThai = TrangThai.PENDING;
}