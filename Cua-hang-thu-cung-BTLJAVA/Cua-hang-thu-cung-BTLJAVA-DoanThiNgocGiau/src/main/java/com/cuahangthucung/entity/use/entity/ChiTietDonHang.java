package com.cuahangthucung.entity.use.entity;
import jakarta.persistence.*;
import lombok.Data;
/*Vì bảng có khóa chính kép (MaDH, MaSP) nên JPA không cho dùng nhiều @Id, bắt buộc phải gom lại thành 1 class riêng (ChiTietDonHangId).
Nói đơn giản: file Id sinh ra để đại diện cho khóa chính kép trong Java*/
@Entity
@Data
public class ChiTietDonHang {

    @EmbeddedId
    private ChiTietDonHangId id;

    private int soLuong;
    private double donGia;
}

