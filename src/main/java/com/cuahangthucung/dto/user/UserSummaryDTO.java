package com.cuahangthucung.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDTO {
    private Long tongSoUser;
    private Long soUserActive;
    private Long soUserInactive;
    private Long soNhanVien;
    private Long soKhachHang;
}