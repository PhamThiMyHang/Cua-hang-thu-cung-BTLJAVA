package com.cuahangthucung.entity.use.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "NhaCungCap")
@Data
public class NhaCungCap {

    @Id
    @Column(name = "MaNCC", length = 50)
    @NotBlank(message = "Mã nhà cung cấp không được để trống")
    @Size(max = 50, message = "Mã nhà cung cấp không được vượt quá 50 ký tự")
    private String maNCC;

    @Column(name = "TenNCC", nullable = false, length = 100)
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    @Size(min = 2, max = 100, message = "Tên nhà cung cấp phải từ 2 đến 100 ký tự")
    private String tenNCC;

    @Column(name = "SDT", nullable = false, unique = true, length = 15)
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Số điện thoại không hợp lệ (phải từ 9 đến 15 chữ số)")
    private String sdt;

    @Column(name = "DiaChi", nullable = false, length = 255)
    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String diaChi;

    // QUAN HỆ: Một Nhà cung cấp có thể cung cấp nhiều Sản phẩm
    // CascadeType.ALL kết hợp với ON DELETE CASCADE ở DB: Xóa NCC sẽ tự động xóa các sản phẩm thuộc NCC đó
    @OneToMany(mappedBy = "nhaCungCap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<SanPham> danhSachSanPham;
}