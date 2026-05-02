package com.cuahangthucung.entity.use.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NhaCungCap {

    @Id
    private String maNCC;

    private String tenNCC;
    private String sdt;
    private String diaChi;
}