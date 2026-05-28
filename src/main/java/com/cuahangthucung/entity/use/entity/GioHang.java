package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "GIOHANG")
@Data
public class GioHang {

    @EmbeddedId
    private GioHangId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaUser", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @Column(name = "SoLuong", nullable = false)
    private Integer soLuong = 1;
}