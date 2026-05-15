package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LichTrucDTO {
    private Integer id;
    private Integer maNV;
    private String tenNV;

    private LocalDate ngay;
    private String caLamViec;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
}