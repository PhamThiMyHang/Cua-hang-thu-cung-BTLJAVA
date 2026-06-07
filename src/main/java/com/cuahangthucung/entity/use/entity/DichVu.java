package com.cuahangthucung.entity.use.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "DichVu")
@Data
public class DichVu {

    @Id
    @Column(name = "MaDV", length = 50)
    @NotBlank(message = "Mã dịch vụ không được để trống")
    @Size(max = 50, message = "Mã dịch vụ không được vượt quá 50 ký tự")
    private String maDV;

    @Column(name = "TenDV", nullable = false, length = 100)
    @NotBlank(message = "Tên dịch vụ không được để trống")
    @Size(min = 2, max = 100, message = "Tên dịch vụ phải từ 2 đến 100 ký tự")
    private String tenDV;

    @Column(name = "Gia", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Giá dịch vụ không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá dịch vụ phải lớn hơn hoặc bằng 0")
    private BigDecimal gia = BigDecimal.ZERO;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    // QUAN HỆ: Một Dịch vụ có thể xuất hiện trong nhiều Lịch hẹn
    // CascadeType.ALL kết hợp với ON DELETE CASCADE ở DB, @ToString.Exclude và @JsonIgnore để tránh vòng lặp vô hạn
    @OneToMany(mappedBy = "dichVu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<LichHen> danhSachLichHen;

    // Trong file DichVu.java
    @Column(name = "URL_img", length = 500)
    private String urlImg;
}