package com.cuahangthucung.dto.user.chamcong;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ChamCongSearchRequest {
    private Integer maNV;
    private LocalDate ngay;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private String keyword;
}