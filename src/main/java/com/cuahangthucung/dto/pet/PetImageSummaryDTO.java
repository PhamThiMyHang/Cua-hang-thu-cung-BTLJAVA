package com.cuahangthucung.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetImageSummaryDTO {
    private Long tongSoLuongAnh;
    private Long soAnhCuaPet;       // Kết quả cho yêu cầu count theo MaPet
    private Long soAnhTrongNgay;    // Kết quả cho yêu cầu count theo ngày
    private Long soAnhPetTheoNgay;  // Kết quả cho yêu cầu count theo Pet + Ngày
}