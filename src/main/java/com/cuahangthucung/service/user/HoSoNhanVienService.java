package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface HoSoNhanVienService extends BaseService<HoSoNhanVien, Integer> {
    List<HoSoNhanVienDTO> search(HoSoNhanVienSearchRequest request);
    HoSoNhanVienDTO saveRequest(HoSoNhanVienRequest request);
    HoSoNhanVienDTO findByIdDTO(Integer id);
    HoSoNhanVienDTO findByMaNV(Integer maNV);
    List<HoSoNhanVienDTO> findAllDTO();
}