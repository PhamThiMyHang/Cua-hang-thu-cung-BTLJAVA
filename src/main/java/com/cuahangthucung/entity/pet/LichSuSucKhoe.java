package com.cuahangthucung.entity.pet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "LICHSU_SUC_KHOE")
@Data
public class LichSuSucKhoe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLS")
    private Integer maLS;

    @NotBlank(message = "Mã Pet không được để trống")
    @Column(name = "MaPet", length = 20, nullable = false)
    private String maPet;

    @NotBlank(message = "Mô tả sức khỏe không được để trống")
    @Size(max = 300, message = "Mô tả không được vượt quá 300 ký tự")
    @Column(name = "MoTa", length = 300, nullable = false)
    private String moTa;

    @NotNull
    @Column(name = "Ngay", nullable = false, updatable = false)
    private LocalDateTime ngay;

    @NotNull(message = "Loại lịch sử không được để trống")
    @Enumerated(EnumType.STRING) // Đồng bộ với ENUM('Vaccine', 'Benh', 'Kham')
    @Column(name = "Loai", nullable = false)
    private LoaiLichSu loai;

    @PrePersist
    protected void onCreate() {
        if (this.ngay == null) {
            this.ngay = LocalDateTime.now();
        }
    }
}