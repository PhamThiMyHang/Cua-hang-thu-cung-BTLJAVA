package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LichSuSucKhoeDTO {
    private Integer maLS;

    // Chỉ giữ lại mã Pet thay vì cả đối tượng Pet để tránh vòng lặp JSON
    private String maPet;
    private String tenPet; // Thêm tên Pet để Frontend hiển thị tiêu đề nếu cần

    private String moTa;
    private LocalDateTime ngay;

    // Trả về String của Enum (Vaccine, Benh, Kham)
    private String loai;
}