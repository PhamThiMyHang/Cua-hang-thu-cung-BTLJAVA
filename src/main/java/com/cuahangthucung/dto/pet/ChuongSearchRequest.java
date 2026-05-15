package com.cuahangthucung.dto.pet;

import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import lombok.Data;

@Data
public class ChuongSearchRequest {
    private String maChuong;
    private String maLoaiChuong;
    private TrangThaiChuong trangThai;
}