package com.cuahangthucung.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "LICH_SU_DANG_NHAP")
@Data
public class LichSuDangNhap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLS;

    private LocalDateTime thoiGian = LocalDateTime.now();

    private String trangThai; // SUCCESS / FAIL

    private Integer userID;
}