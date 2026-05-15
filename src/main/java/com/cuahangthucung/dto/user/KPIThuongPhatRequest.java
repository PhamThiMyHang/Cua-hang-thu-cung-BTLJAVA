package com.cuahangthucung.dto.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class KPIThuongPhatRequest {
    private Integer maKPI;

    @NotNull(message = "Mã nhân viên không được để trống")
    private Integer maNV;

    @NotBlank(message = "Tháng không được để trống")
    private String thang;   // YYYY-MM

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal thuong = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal phat = BigDecimal.ZERO;
}