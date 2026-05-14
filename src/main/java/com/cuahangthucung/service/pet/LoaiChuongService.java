package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface LoaiChuongService extends BaseService<LoaiChuong, String> {
    // Tìm kiếm phân trang/lọc động
    List<LoaiChuongDTO> search(LoaiChuongSearchRequest request);

    // Lưu từ Request (Add/Update)
    LoaiChuongDTO saveRequest(LoaiChuongRequest request);

    List<LoaiChuongDTO> findAllDTO();

    LoaiChuongDTO findByIdDTO(String id);

    // Lấy thống kê tổng hợp cho Dashboard
    LoaiChuongSummaryDTO getSummary();
}