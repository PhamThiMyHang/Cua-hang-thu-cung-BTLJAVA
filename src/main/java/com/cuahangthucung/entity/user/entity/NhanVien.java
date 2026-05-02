package com.cuahangthucung.entity.user.entity;

import com.cuahangthucung.entity.user.enums.ChucVu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "NHANVIEN")
@Data
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNV")
    private Integer maNV;

    @NotBlank(message = "Tên nhân viên không được để trống")
    @Column(name = "TenNV", nullable = false, length = 100)
    private String tenNV;

    @Column(name = "SDT", length = 15, unique = true)
    private String sdt;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @NotNull(message = "Chức vụ không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "ChucVu", nullable = false)
    private ChucVu chucVu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    @ToString.Exclude
    private User user;

    // Quan hệ ngược
    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ChamCong> danhSachChamCong;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LichTruc> danhSachLichTruc;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<KPIThuongPhat> danhSachKPI;
}