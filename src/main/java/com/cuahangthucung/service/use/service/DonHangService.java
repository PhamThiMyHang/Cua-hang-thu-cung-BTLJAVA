package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.donhang.DonHangDTO;
import com.cuahangthucung.dto.use.donhang.DonHangRequest;
import com.cuahangthucung.dto.use.donhang.DonHangSearchRequest;
import com.cuahangthucung.dto.use.donhang.DonHangSummaryDTO;
import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.enums.TrangThai;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface DonHangService extends BaseService<DonHang, String> {

    // ĐÃ SỬA: Chuyển đổi sang Page và nhận Pageable để hỗ trợ phân trang động
    Page<DonHangDTO> search(DonHangSearchRequest request, Pageable pageable);

    // Lưu mới / Cập nhật từ DonHangRequest
    DonHangDTO saveRequest(DonHangRequest request);

    // Lấy chi tiết DTO theo mã
    DonHangDTO findByIdDTO(String id);

    // Lấy toàn bộ danh sách dạng DTO
    List<DonHangDTO> findAllDTO();

    // Lấy thống kê tổng hợp
    DonHangSummaryDTO getSummary();

    // Sinh mã đơn hàng tự động
    String generateNextMaDH();

    // Cập nhật trạng thái đơn hàng
    DonHangDTO capNhatTrangThai(String maDH, TrangThai trangThai);

    // Lọc đơn hàng theo khách hàng
    List<DonHangDTO> findByMaKH(String maKH);

    // Lọc đơn hàng theo nhân viên
    List<DonHangDTO> findByMaNV(String maNV);

    // Lọc đơn hàng trong khoảng thời gian
    List<DonHangDTO> findByNgayTaoRange(LocalDate tuNgay, LocalDate denNgay);

    // Tính doanh thu trong khoảng thời gian
    Double tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay);
}