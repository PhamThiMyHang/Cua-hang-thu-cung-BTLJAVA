package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface KhachHangService extends BaseService<KhachHang, Integer> {
    List<KhachHangDTO> search(KhachHangSearchRequest request);
    KhachHangDTO saveRequest(KhachHangRequest request);
    KhachHangDTO findByIdDTO(Integer id);
    List<KhachHangDTO> findAllDTO();
    KhachHangSummaryDTO getSummary();
}