package com.cuahangthucung.dto.use.yeuthich;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class YeuThichRequest {

    @NotBlank(message = "Mã người dùng không được để trống")
    private String maUser;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSP;
}