package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LichSuDangNhapSearchRequest {
    private Integer userID;
    private String username;
    private String trangThai;
    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;
}