package com.cuahangthucung.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienSummaryDTO {
    private Long tongSoNhanVien;
    private Long soNhanVienStaff;
    private Long soNhanVienKTV;
    private Long soNhanVienCoTaiKhoan;
}