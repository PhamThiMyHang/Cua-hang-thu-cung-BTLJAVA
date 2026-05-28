package com.cuahangthucung.service.pet.service;

import com.cuahangthucung.dto.pet.lichsu.LichSuSearchRequest;
import com.cuahangthucung.dto.pet.lichsu.LichSuSucKhoeDTO;
import com.cuahangthucung.dto.pet.lichsu.LichSuSucKhoeRequest;
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface LichSuSucKhoeService extends BaseService<LichSuSucKhoe, Integer> {
    List<LichSuSucKhoeDTO> search(LichSuSearchRequest request);
    LichSuSucKhoeDTO saveRequest(LichSuSucKhoeRequest request);
    // BỔ SUNG: Tìm theo ID (Yêu cầu 1)
    LichSuSucKhoeDTO findByIdDTO(Integer id);

    // BỔ SUNG: Hàm tính số bản ghi (Yêu cầu count 2-8)
    long countByRequest(LichSuSearchRequest request);
    LichSuSucKhoeDTO convertToDTO(LichSuSucKhoe entity);


}

