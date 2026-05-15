package com.cuahangthucung.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LichTrucRequest {
    private Integer id;

    @NotNull(message = "Mã nhân viên không được để trống")
    private Integer maNV;

    @NotNull(message = "Ngày làm việc không được để trống")
    private LocalDate ngay;

    @NotBlank(message = "Ca làm việc không được để trống")
    private String caLamViec;

    @NotNull(message = "Giờ bắt đầu không được để trống")
    private LocalTime gioBatDau;

    @NotNull(message = "Giờ kết thúc không được để trống")
    private LocalTime gioKetThuc;
}