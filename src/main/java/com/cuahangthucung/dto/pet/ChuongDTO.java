package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ChuongDTO {
    private String maChuong;
    private String trangThai; // Chuyển Enum (TRONG, KIN, SUA_CHUA) thành String

    // Thông tin làm phẳng từ LoaiChuong
    private String maLoaiChuong;
    private String tenLoaiChuong;
    private BigDecimal giaThue;

    // (Tùy chọn) Số lượng Pet hiện đang ở trong chuồng này
    // Phục vụ cho việc hiển thị nhanh trên danh sách chuồng
    private Integer soLuongPetHienTai;
}