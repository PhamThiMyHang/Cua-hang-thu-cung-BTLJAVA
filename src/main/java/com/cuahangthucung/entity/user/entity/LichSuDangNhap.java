package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "LICH_SU_DANG_NHAP")
@Data
public class LichSuDangNhap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLS")
    private Integer maLS;

    @NotNull(message = "Người dùng không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "ThoiGian", nullable = false, updatable = false)
    private LocalDateTime thoiGian;

    @Column(name = "TrangThai", length = 20)
    private String trangThai;   // SUCCESS / FAIL

    @PrePersist
    protected void onCreate() {
        if (this.thoiGian == null) {
            this.thoiGian = LocalDateTime.now();
        }
    }
}