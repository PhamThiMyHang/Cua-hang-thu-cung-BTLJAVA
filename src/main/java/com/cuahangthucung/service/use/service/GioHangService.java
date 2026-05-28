package com.cuahangthucung.service.use.service;


import com.cuahangthucung.dto.use.giohang.GioHangDTO;
import com.cuahangthucung.dto.use.giohang.GioHangRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSearchRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSummaryDTO;
import com.cuahangthucung.entity.use.entity.GioHang;
import com.cuahangthucung.entity.use.entity.GioHangId;
import com.cuahangthucung.service.base.BaseService;

import java.math.BigDecimal;
import java.util.List;

public interface GioHangService extends BaseService<GioHang, GioHangId> {

    List<GioHangDTO> search(GioHangSearchRequest request);

    GioHangDTO saveRequest(GioHangRequest request);

    GioHangDTO findByIdDTO(String maGioHang, String maSP);

    List<GioHangDTO> findByMaGioHang(String maGioHang);

    List<GioHangDTO> findByMaUser(String maUser);

    BigDecimal tinhTongTien(String maGioHang);

    Integer countSoLuongSanPhamTrongGio(String maUser);

    GioHangSummaryDTO getSummary();

    boolean existsByUserAndSanPham(String maUser, String maSP);

    void deleteByMaGioHangAndMaSP(String maGioHang, String maSP);

    GioHangDTO convertToDTO(GioHang entity);
}