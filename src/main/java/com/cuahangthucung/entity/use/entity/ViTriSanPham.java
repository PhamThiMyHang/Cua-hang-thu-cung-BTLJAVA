package com.cuahangthucung.entity.use.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "ViTriSanPham")
@Data
public class ViTriSanPham {

    @Id
    @Column(name = "MaViTri", length = 50)
    private String maViTri;

    @Column(name = "ViTri", nullable = false, length = 100)
    private String viTri;

    @OneToMany(mappedBy = "viTriSanPham",
            fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<SanPham> danhSachSanPham;
}