package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "KPI_THUONGPHAT")
@Data
public class KPIThuongPhat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKPI;

    private String thang;

    @Column(nullable = false)
    private Double thuong = 0.0;

    @Column(nullable = false)
    private Double phat = 0.0;

    private Integer maNV;
}