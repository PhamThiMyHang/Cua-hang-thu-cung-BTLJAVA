package com.cuahangthucung.dto.use.dichvu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DichVuRequest {

    private String maDV;

    @NotBlank(message = "Tên dịch vụ không được để trống")
    private String tenDV;

    @Positive(message = "Giá dịch vụ phải lớn hơn 0")
    private BigDecimal gia;

    private String moTa;

    // Thêm vào class DichVuRequest
    private String urlImg;

}