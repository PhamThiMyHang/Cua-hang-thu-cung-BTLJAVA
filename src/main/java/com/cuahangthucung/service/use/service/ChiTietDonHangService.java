package com.cuahangthucung.service.use.service;
import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangDTO;
import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangRequest;
import com.cuahangthucung.entity.use.entity.ChiTietDonHang;
import com.cuahangthucung.entity.use.entity.ChiTietDonHangId;
import com.cuahangthucung.service.base.BaseService;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietDonHangService extends BaseService<ChiTietDonHang, ChiTietDonHangId> {

    // Lấy toàn bộ chi tiết theo mã đơn hàng
    List<ChiTietDonHangDTO> findByMaDH(String maDH);

    // Lấy toàn bộ chi tiết theo mã sản phẩm
    List<ChiTietDonHangDTO> findByMaSP(String maSP);

    // Thêm / Cập nhật chi tiết đơn hàng
    ChiTietDonHangDTO saveRequest(ChiTietDonHangRequest request);

    // Lấy chi tiết DTO theo khóa chính kép
    ChiTietDonHangDTO findByIdDTO(String maDH, String maSP);

    // Xóa toàn bộ chi tiết của một đơn hàng
    void deleteByMaDH(String maDH);

    // Tính tổng tiền của một đơn hàng từ chi tiết (Sửa từ Double sang BigDecimal)
    BigDecimal tinhTongTien(String maDH);
}