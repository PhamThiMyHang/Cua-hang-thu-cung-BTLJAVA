package com.cuahangthucung.dto.pet;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PetImageRequest {
    private Integer maImg; // Dùng khi Update, null khi Create

    @NotBlank(message = "URL hình ảnh không được để trống")
    private String url;

    @NotBlank(message = "Mã thú cưng không được để trống")
    private String maPet;

    // Thêm trường tên pet
    private String tenPet;

}