package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class KhuyenMai {

    @Id
    private String maKM;

    private String tenKM;

    private double giamGia;

    private String ngayBD;
    private String ngayKT;
}
