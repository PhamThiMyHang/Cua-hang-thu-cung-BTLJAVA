package com.cuahangthucung.dto.use.nhacungcap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCapDTO {

    private String maNCC;
    private String tenNCC;
    private String sdt;
    private String diaChi;

    // Thông tin bổ sung
    private Long soSanPhamCungCap;
    private List<String> danhSachSanPham; // optional
}