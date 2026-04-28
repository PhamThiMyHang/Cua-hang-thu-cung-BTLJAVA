package com.cuahangthucung.entity.pet.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "LOAICHUONG")
@Data
public class LoaiChuong {
    @Id
    @Column(name = "MaLoaiChuong", length = 20)
    private String maLoaiChuong;

    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    @Column(name = "TenLoai", nullable = true)
    private String tenLoai;


    @NotNull(message = "Gia thue không được để trống")
    @Column(name = "GiaThue")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá phải lớn hơn 0")
    private BigDecimal giaThue= BigDecimal.ZERO;


    @NotNull(message = "So luong không được để trống")
    @Min(value = 0, message = "So luong không được là số âm")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong = 0;

    // Định nghĩa: Một Loại chuồng có nhiều Chuồng cụ thể
    @OneToMany(mappedBy = "loaiChuong", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // Tránh vòng lặp vô hạn khi in log
    private List<Chuong> danhSachChuong;
}