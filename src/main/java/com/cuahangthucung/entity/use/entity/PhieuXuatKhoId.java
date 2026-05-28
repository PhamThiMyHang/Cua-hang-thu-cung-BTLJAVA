package com.cuahangthucung.entity.use.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuXuatKhoId implements Serializable {
    private String maPhieu; // Đại diện cho thuộc tính MaPhieu
    private String sanPham; // BẮT BUỘC phải trùng với tên biến 'sanPham' trong thực thể PhieuXuatKho
}