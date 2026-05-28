package com.cuahangthucung.service.use.service;


import com.cuahangthucung.dto.use.yeuthich.YeuThichDTO;
import com.cuahangthucung.dto.use.yeuthich.YeuThichRequest;
import com.cuahangthucung.dto.use.yeuthich.YeuThichSearchRequest;
import com.cuahangthucung.entity.use.entity.YeuThich;
import com.cuahangthucung.entity.use.entity.YeuThichId;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface YeuThichService extends BaseService<YeuThich, YeuThichId> {

    List<YeuThichDTO> search(YeuThichSearchRequest request);

    YeuThichDTO saveRequest(YeuThichRequest request);

    List<YeuThichDTO> findByMaUser(String maUser);

    boolean existsByUserAndSanPham(String maUser, String maSP);

    void deleteByUserAndSanPham(String maUser, String maSP);

    YeuThichDTO convertToDTO(YeuThich entity);
}