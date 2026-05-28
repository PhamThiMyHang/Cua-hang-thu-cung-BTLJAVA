package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GioHangId implements Serializable {

    private String maGioHang;
    private String maSP;
}