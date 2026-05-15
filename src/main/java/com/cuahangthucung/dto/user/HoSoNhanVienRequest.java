package com.cuahangthucung.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HoSoNhanVienRequest {
    private Integer maHoSo;

    @NotNull(message = "Mã nhân viên không được để trống")
    private Integer maNV;

    private String bangCap;
    private String kinhNghiem;
    private String trinhDo;
}