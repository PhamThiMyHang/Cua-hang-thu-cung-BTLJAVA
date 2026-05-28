package com.cuahangthucung.entity.use.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhapKhoId implements Serializable {
    private String maPhieu;
    private String sanPham; // Đổi từ sanPham thành maSP để khớp với findById_MaSP trong Repository
}