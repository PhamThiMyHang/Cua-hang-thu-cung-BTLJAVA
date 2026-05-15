package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhachHangService extends BaseService<KhachHang, Integer> {

    List<KhachHangDTO> search(KhachHangSearchRequest request);
    
    // Phiên bản có phân trang + sắp xếp
    Page<KhachHangDTO> search(KhachHangSearchRequest request, Pageable pageable);

    KhachHangDTO saveRequest(KhachHangRequest request);
    KhachHangDTO findByIdDTO(Integer id);
    List<KhachHangDTO> findAllDTO();
    KhachHangSummaryDTO getSummary();
    String generateNextMaKH();
}