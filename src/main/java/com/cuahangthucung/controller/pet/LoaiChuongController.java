package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;

import com.cuahangthucung.dto.pet.*; // Hoặc dto.pet.* tùy cấu trúc folder của bạn
import com.cuahangthucung.dto.pet.LoaiChuongRequest;
import com.cuahangthucung.service.pet.LoaiChuongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/loai-chuong")
@CrossOrigin("*")
public class LoaiChuongController extends BaseController {

    private final LoaiChuongService loaiChuongService;

    public LoaiChuongController(LoaiChuongService loaiChuongService) {
        this.loaiChuongService = loaiChuongService;
    }


    /**
     * 1. Lọc động danh mục loại chuồng
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(LoaiChuongSearchRequest request) {
        List<LoaiChuongDTO> list = loaiChuongService.search(request);
        return resSuccess(list, "Tìm kiếm loại chuồng thành công");
    }

    /**
     * 2. Lấy tất cả (Dùng DTO để có thông tin soChuongConTrong)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<LoaiChuongDTO> list = loaiChuongService.findAllDTO();
        return resSuccess(list, "Lấy danh sách loại chuồng thành công");
    }

    /**
     * 3. Lấy chi tiết
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        LoaiChuongDTO dto = loaiChuongService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy loại chuồng");
    }

    /**
     * 4. Thêm mới - Dùng RequestDTO thay vì Entity để an toàn dữ liệu
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LoaiChuongRequest request) {
        LoaiChuongDTO saved = loaiChuongService.saveRequest(request);
        return resCreated(saved, "Thêm loại chuồng thành công");
    }

    /**
     * 5. Cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody LoaiChuongRequest request) {
        request.setMaLoaiChuong(id);
        LoaiChuongDTO updated = loaiChuongService.saveRequest(request);
        return resSuccess(updated, "Cập nhật loại chuồng thành công");
    }

    /**
     * 6. Xóa
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        loaiChuongService.deleteById(id);
        return resSuccess(null, "Xóa loại chuồng thành công");
    }


    /**
     * 7. Thống kê loại chuồng cho Dashboard
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        LoaiChuongSummaryDTO summary = loaiChuongService.getSummary();
        return resSuccess(summary, "Lấy thống kê loại chuồng thành công");
    }

}