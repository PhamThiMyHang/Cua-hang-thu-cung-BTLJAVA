package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LichSuSearchRequest {
    private String maPet;
    private String loai; // Vaccine, Benh, Kham
    private String tuKhoa; // Tìm trong MoTa
    private LocalDate ngay;
<<<<<<< HEAD
    private String tenPet;
=======
>>>>>>> DoanThiNgocGiau
}