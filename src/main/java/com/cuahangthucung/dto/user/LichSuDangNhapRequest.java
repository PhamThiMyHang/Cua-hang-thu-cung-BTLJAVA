package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LichSuDangNhapRequest {
    private Integer userID;
    private String trangThai;   // SUCCESS / FAIL
    private LocalDateTime thoiGian;
}