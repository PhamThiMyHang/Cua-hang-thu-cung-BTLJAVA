package com.cuahangthucung.service.user.service;

import com.cuahangthucung.dto.user.hosonhanvien.HoSoNhanVienDTO;
import com.cuahangthucung.dto.user.hosonhanvien.HoSoNhanVienRequest;
import com.cuahangthucung.dto.user.hosonhanvien.HoSoNhanVienSearchRequest;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HoSoNhanVienService extends BaseService<HoSoNhanVien, Integer> {

    List<HoSoNhanVienDTO> search(HoSoNhanVienSearchRequest request);
    
    // Phiên bản có phân trang
    Page<HoSoNhanVienDTO> search(HoSoNhanVienSearchRequest request, Pageable pageable);

    HoSoNhanVienDTO saveRequest(HoSoNhanVienRequest request);
    HoSoNhanVienDTO findByIdDTO(Integer id);
    HoSoNhanVienDTO findByMaNV(Integer maNV);
    List<HoSoNhanVienDTO> findAllDTO();
}