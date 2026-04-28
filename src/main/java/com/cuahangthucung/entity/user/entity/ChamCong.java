package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CHAMCONG")
@Data
public class ChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maCC;

    private LocalDate ngay;

    private LocalTime gioVao;

    private LocalTime gioRa;

    private Integer maNV;
}