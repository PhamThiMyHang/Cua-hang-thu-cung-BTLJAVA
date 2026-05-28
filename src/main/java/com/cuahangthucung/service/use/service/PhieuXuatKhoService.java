package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoDTO;
import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoRequest;
import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuXuatKho;
import com.cuahangthucung.entity.use.entity.PhieuXuatKhoId;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhieuXuatKhoService extends BaseService<PhieuXuatKho, PhieuXuatKhoId> {
    Page<PhieuXuatKhoDTO> search(PhieuXuatKhoSearchRequest request, Pageable pageable);
    List<PhieuXuatKhoDTO> findByMaPhieu(String maPhieu);
    PhieuXuatKhoDTO findById(String maPhieu, String maSP);
    PhieuXuatKhoDTO saveRequest(PhieuXuatKhoRequest request);
}