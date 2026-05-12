package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

/*
 * Khóa chính kép (MaDH, MaSP) của bảng ChiTietDonHang.
 * Phải implement Serializable và override equals/hashCode (Lombok @Data lo phần này).
 */
@Embeddable
@Data
public class ChiTietDonHangId implements Serializable {

    private String maDH;
    private String maSP;
}
