package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhanVienService extends BaseService<NhanVien, Integer> {

    List<NhanVienDTO> search(NhanVienSearchRequest request);
    
    // Phiên bản có phân trang + sắp xếp (dùng cho Controller)
    Page<NhanVienDTO> search(NhanVienSearchRequest request, Pageable pageable);

    NhanVienDTO saveRequest(NhanVienRequest request);
    NhanVienDTO findByIdDTO(Integer id);
    List<NhanVienDTO> findAllDTO();
    NhanVienSummaryDTO getSummary();
    
    /*Bổ sung*/
    String generateNextMaNV();
}