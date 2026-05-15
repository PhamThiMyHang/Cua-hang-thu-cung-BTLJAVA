package com.cuahangthucung.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ChamCongRequest {
    private Integer maCC;

    @NotNull(message = "Mã nhân viên không được để trống")
    private Integer maNV;

    @NotNull(message = "Ngày không được để trống")
    private LocalDate ngay;

    private LocalTime gioVao;
    private LocalTime gioRa;
}