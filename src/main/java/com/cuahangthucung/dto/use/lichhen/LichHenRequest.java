package com.cuahangthucung.dto.use.lichhen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LichHenRequest {

    private String maLich;

    @NotBlank
    private String maKH;

    private String maPet;

    @NotBlank
    private String maNV;

    @NotBlank
    private String maDV;

    @NotNull
    private LocalDateTime thoiGian;

    private String trangThai;
}