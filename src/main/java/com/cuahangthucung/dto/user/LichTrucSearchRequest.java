package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LichTrucSearchRequest {
    private Integer maNV;
    private LocalDate ngay;
    private String caLamViec;
    private LocalDate tuNgay;
    private LocalDate denNgay;
}