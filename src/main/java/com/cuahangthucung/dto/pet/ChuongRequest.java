package com.cuahangthucung.dto.pet;

import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import lombok.Data;

@Data
public class ChuongRequest {
    private String maChuong; // Nhập tay hoặc tự sinh tùy bạn
    private String maLoaiChuong; // Nhận ID từ dropdown của Frontend
    private TrangThaiChuong trangThai;
}