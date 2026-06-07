package com.cuahangthucung.dto.use.lichhen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichHenDTO {

    private String maLich;
    private String maKH;
    private String tenKH;
    private String maPet;
    private String tenPet;
    private String maNV;
    private String tenNV;
    private String maDV;
    private String tenDV;
    private BigDecimal giaDV;
    private LocalDateTime thoiGian;
    private String trangThai;
}