package com.cuahangthucung.entity.pet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

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
}