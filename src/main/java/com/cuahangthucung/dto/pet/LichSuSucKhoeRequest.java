package com.cuahangthucung.dto.pet;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LichSuSucKhoeRequest {
    private Integer maLS;

    @NotBlank(message = "Mã thú cưng không được để trống")
    private String maPet;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 300)
    private String moTa;

    @NotBlank(message = "Loại lịch sử không được để trống")
    private String loai; // Nhận từ Enum LoaiLichSu.name()
}