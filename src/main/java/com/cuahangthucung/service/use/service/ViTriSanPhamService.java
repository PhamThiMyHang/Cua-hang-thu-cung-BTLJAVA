package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamDTO;
import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamRequest;

import java.util.List;

public interface ViTriSanPhamService {

    List<ViTriSanPhamDTO> findAll();

    ViTriSanPhamDTO findById(String maViTri);

    ViTriSanPhamDTO create(ViTriSanPhamRequest request);

    ViTriSanPhamDTO update(String maViTri, ViTriSanPhamRequest request);

    void delete(String maViTri);
}