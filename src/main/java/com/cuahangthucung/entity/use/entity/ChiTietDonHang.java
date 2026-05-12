package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

/*
 * Vì bảng có khóa chính kép (MaDH, MaSP) nên JPA không cho dùng nhiều @Id,
 * bắt buộc phải gom lại thành 1 class riêng (ChiTietDonHangId).
 */
@Entity
@Data
public class ChiTietDonHang {

    @EmbeddedId
    private ChiTietDonHangId id;

    // SoLuong > 0 (CHECK trong SQL)
    private int soLuong;

    private double donGia;
}