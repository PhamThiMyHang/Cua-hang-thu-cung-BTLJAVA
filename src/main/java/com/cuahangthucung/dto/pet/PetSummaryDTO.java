package com.cuahangthucung.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSummaryDTO {
    private Long tongSoLuongPet;
    private BigDecimal tongGiaTriPet;
    private Long soPetDangBenh; // Tính từ số lượng Pet có TinhTrangPet.BENH
    private Long soPetMoiTrongThang;
}