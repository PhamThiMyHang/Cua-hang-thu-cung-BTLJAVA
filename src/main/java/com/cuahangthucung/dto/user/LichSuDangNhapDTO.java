package com.cuahangthucung.dto.user;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LichSuDangNhapDTO {
    private Integer maLS;
    private Integer userID;
    private String username;

    private LocalDateTime thoiGian;
    private String trangThai;   // SUCCESS / FAIL
}