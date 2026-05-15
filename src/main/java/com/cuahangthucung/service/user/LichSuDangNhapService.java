package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LichSuDangNhapService extends BaseService<LichSuDangNhap, Integer> {

    List<LichSuDangNhapDTO> search(LichSuDangNhapSearchRequest request);
    
    // Phiên bản có phân trang + sắp xếp
    Page<LichSuDangNhapDTO> search(LichSuDangNhapSearchRequest request, Pageable pageable);

    LichSuDangNhapDTO saveRequest(LichSuDangNhapRequest request);
    LichSuDangNhapDTO findByIdDTO(Integer id);
    List<LichSuDangNhapDTO> findAllDTO();
}