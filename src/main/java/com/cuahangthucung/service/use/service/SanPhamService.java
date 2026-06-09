package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.sanpham.SanPhamDTO;
import com.cuahangthucung.dto.use.sanpham.SanPhamRequest;
import com.cuahangthucung.dto.use.sanpham.SanPhamSearchRequest;
import com.cuahangthucung.dto.use.sanpham.SanPhamSummaryDTO;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SanPhamService extends BaseService<SanPham, String> {
    Page<SanPhamDTO> search(SanPhamSearchRequest request, Pageable pageable);
    List<SanPhamDTO> findAllDTO();
    SanPhamDTO findByIdDTO(String id);
    SanPhamDTO saveRequest(SanPhamRequest request);
    String generateNextMaSP();

    /* --- BỔ SUNG --- */
    // Thống kê kho với ngưỡng cảnh báo tùy chọn (ví dụ: dưới 5)
    SanPhamSummaryDTO getSummary(Integer nguongCanhBao);

    long countByViTri(String maViTri);
}