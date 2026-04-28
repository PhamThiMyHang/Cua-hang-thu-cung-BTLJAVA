package com.cuahangthucung.entity.pet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "PET_IMAGE")
@Data
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaImg")
    private Integer maImg; // Khớp với INT AUTO_INCREMENT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPet", nullable = false)
    @NotNull(message = "Thông tin thú cưng không được để trống")
    @ToString.Exclude // Tránh vòng lặp vô hạn khi in log
    private Pet pet;

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