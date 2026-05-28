package com.cuahangthucung.dto.user.NhanVien;

import com.cuahangthucung.dto.user.hosonhanvien.HoSoNhanVienDTO;
import lombok.Data;

@Data
public class NhanVienDTO {
    private Integer maNV;
    private String tenNV;
    private String sdt;
    private String diaChi;
    private String chucVu;
    private String gmail;            // Bổ sung gmail của nhân viên

    // Thông tin tài khoản liên kết
    private Integer userID;
    private String username;
    private String gmailUser;        // Bổ sung gmail của tài khoản liên kết

    private HoSoNhanVienDTO hoSo;
}