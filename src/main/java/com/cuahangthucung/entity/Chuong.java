package com.cuahangthucung.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CHUONG")
@Data
public class Chuong {
    @Id
    @Column(name = "MaChuong")
    private String maChuong;

    @Column(name = "MaLoaiChuong")
    private String maLoaiChuong; // Bạn có thể map thành đối tượng LoaiChuong sau này

    @Column(name = "TrangThai")
    private String trangThai;
}