package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PetImageDTO {
    private Integer maImg;

    // Chỉ giữ lại mã Pet để nhận diện, tránh lặp dữ liệu
    private String maPet;

    private String url;
    private LocalDateTime thoiGianDangTai;
}