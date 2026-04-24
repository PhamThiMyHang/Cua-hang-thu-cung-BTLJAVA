package com.cuahangthucung.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "PET_IMAGE")
@Data
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaImg")
    private Integer maImg; // Khớp với INT AUTO_INCREMENT

    @NotBlank(message = "Mã thú cưng không được để trống")
    @Column(name = "MaPet", length = 20, nullable = false)
    private String maPet;

    @NotBlank(message = "URL hình ảnh không được để trống")
    @Column(name = "Url", length = 500, nullable = false)
    private String url;

    @Column(name = "ThoiGianDangTai", nullable = false, updatable = false)
    private LocalDateTime thoiGianDangTai;

    @PrePersist
    protected void onCreate() {
        if (this.thoiGianDangTai == null) {
            this.thoiGianDangTai = LocalDateTime.now();
        }
    }
}