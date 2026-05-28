package com.cuahangthucung.dto.use.khuyenmai;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class KhuyenMaiRequest {

    private String maKM;

    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String tenKM;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal giamGia;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBD;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate ngayKT;
}