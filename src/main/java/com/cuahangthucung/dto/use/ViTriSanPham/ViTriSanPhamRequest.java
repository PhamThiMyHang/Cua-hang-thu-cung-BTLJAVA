package com.cuahangthucung.dto.use.ViTriSanPham;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ViTriSanPhamRequest {

    @NotBlank(message="Mã vị trí không được để trống")
    private String maViTri;

    @NotBlank(message="Tên vị trí không được để trống")
    private String viTri;
}