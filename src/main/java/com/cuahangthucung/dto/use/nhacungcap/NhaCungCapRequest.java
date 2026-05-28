package com.cuahangthucung.dto.use.nhacungcap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NhaCungCapRequest {

    private String maNCC;

    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    @Size(max = 100)
    private String tenNCC;

    @Size(max = 15)
    private String sdt;

    @Size(max = 255)
    private String diaChi;
}