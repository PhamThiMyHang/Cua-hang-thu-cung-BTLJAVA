package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PetDTO {
    // Thông tin cơ bản từ Pet Entity
    private String maPet;
    private String tenPet;
    private String giong;
    private Integer tuoi;
    private BigDecimal gia;
    private Float canNang;
    private String tinhTrang; // Chuyển từ TinhTrangPet (Enum) sang String

    // Phẳng hóa thông tin Khách hàng
    private Integer maKH;
    private String tenKH; // Thêm trường này để hiện lên Table/Card

    // Phẳng hóa thông tin Nhân viên
    private Integer maNV;
    private String tenNV; // Thêm trường này

    // Thông tin Chuồng (Lấy từ Object Chuong)
    private String maChuong;
    private String tenLoaiChuong; // Phẳng hóa từ chuong -> loaiChuong -> tenLoai

    // Thời gian
    private LocalDateTime ngayGui;
    private LocalDateTime ngayTra;

    // Danh sách liên kết (Sử dụng DTO thay vì Entity để tránh vòng lặp)
    private List<PetImageDTO> danhSachHinhAnh;
    private List<LichSuSucKhoeDTO> lichSuSucKhoe;
}