package com.cuahangthucung.entity.use.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDonHangId implements Serializable {
    private String donHang; // Phải trùng tên với tên thuộc tính đối tượng đối tác trong Entity chính
    private String sanPham; // Phải trùng tên với tên thuộc tính đối tượng đối tác trong Entity chính
}