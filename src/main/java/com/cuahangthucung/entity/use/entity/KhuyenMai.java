package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class KhuyenMai {

    @Id
    private String maKM;

    private String tenKM;

    // GiamGia BETWEEN 0 AND 100 (CHECK trong SQL)
    private double giamGia;

    // Dùng LocalDate vì SQL dùng kiểu DATE
    private LocalDate ngayBD;
    private LocalDate ngayKT;
}
