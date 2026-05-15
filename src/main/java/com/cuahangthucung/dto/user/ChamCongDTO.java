package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ChamCongDTO {
    private Integer maCC;
    private Integer maNV;
    private String tenNV;

    private LocalDate ngay;
    private LocalTime gioVao;
    private LocalTime gioRa;

    // Tính toán thêm (có thể set ở Service)
    private Integer soGioLam;
    private String trangThaiChamCong;   // ĐÚNG GIỜ, MUỘN, VẮNG
}