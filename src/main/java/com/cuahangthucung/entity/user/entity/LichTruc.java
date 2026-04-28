package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LICHTRUC")
@Data
public class LichTruc {

    @Id
    @Column(name = "ID_LichTruc")
    private Integer id; // dùng đúng cột có sẵn trong DB

    @Column(name = "Ngay")
    private LocalDate ngay;

    @Column(name = "CaLamViec")
    private String caLamViec;

    @Column(name = "GioBatDau")
    private LocalTime gioBatDau;

    @Column(name = "GioKetThuc")
    private LocalTime gioKetThuc;

    @Column(name = "MaNV")
    private Integer maNV;
}