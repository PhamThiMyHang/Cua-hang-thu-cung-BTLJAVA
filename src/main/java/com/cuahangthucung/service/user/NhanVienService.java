package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface NhanVienService extends BaseService<NhanVien, Integer> {
    List<NhanVienDTO> search(NhanVienSearchRequest request);
    NhanVienDTO saveRequest(NhanVienRequest request);
    NhanVienDTO findByIdDTO(Integer id);
    List<NhanVienDTO> findAllDTO();
    NhanVienSummaryDTO getSummary();
}