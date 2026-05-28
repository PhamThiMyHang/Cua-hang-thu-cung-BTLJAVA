package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoDTO;
import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoRequest;
import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuNhapKho;
import com.cuahangthucung.entity.use.entity.PhieuNhapKhoId;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhieuNhapKhoService extends BaseService<PhieuNhapKho, PhieuNhapKhoId> {
    Page<PhieuNhapKhoDTO> search(PhieuNhapKhoSearchRequest request, Pageable pageable);
    List<PhieuNhapKhoDTO> findByMaPhieu(String maPhieu);
    PhieuNhapKhoDTO findById(String maPhieu, String maSP);
    PhieuNhapKhoDTO saveRequest(PhieuNhapKhoRequest request);
}