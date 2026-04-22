package com.cuahangthucung.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "PET_IMAGE")
@Data
public class PetImage {

    @Id
    @Column(name = "MaImg")
    private String maImg;

    @Column(name = "MaPet")
    private String maPet;

    @Column(name = "Url")
    private String url;

    @Column(name = "ThoiGianDangTai", insertable = false, updatable = false)
    private LocalDateTime thoiGianDangTai;

    // Nếu bạn muốn Java tự quản lý thời gian thay vì để DB tự tạo:
    @PrePersist
    protected void onCreate() {
        this.thoiGianDangTai = LocalDateTime.now();
    }
}