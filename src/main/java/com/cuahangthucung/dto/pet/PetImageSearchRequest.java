package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PetImageSearchRequest {
    private Integer maImg; // Phục vụ yêu cầu tìm theo mã ảnh cụ thể
    private String maPet;  // Phục vụ yêu cầu tìm theo mã pet cụ thể

    // Phục vụ yêu cầu: Tìm trong 1 ngày cụ thể (Dùng LocalDate để lọc trọn ngày)
    private LocalDate ngayCuThe;

    // Phục vụ yêu cầu: Tìm trong một khoảng thời gian (Nếu cần)
    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;

    private String tenPet;

}