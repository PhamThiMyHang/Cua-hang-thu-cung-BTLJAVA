package com.cuahangthucung.entity.use.entity;


import com.cuahangthucung.entity.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "YEUTHICH")
@Data
public class YeuThich {

    @EmbeddedId
    private YeuThichId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaUser", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", insertable = false, updatable = false)
    private SanPham sanPham;
}