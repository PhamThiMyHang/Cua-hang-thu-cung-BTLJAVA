package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DichVu {

    @Id
    private String maDV;

    private String tenDV;

    private double gia;

    private String moTa;
}

