package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;

import com.cuahangthucung.dto.pet.*; // Đảm bảo import đúng package DTO của Chuong
import com.cuahangthucung.dto.pet.ChuongSummaryDTO;
import com.cuahangthucung.service.pet.ChuongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
import com.cuahangthucung.service.pet.ChuongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//DoanThiNgocGiau
import java.util.Map;

@RestController
@RequestMapping("/api/chuong")

@CrossOrigin("*")
public class ChuongController extends BaseController {

    private final ChuongService chuongService;

    public ChuongController(ChuongService chuongService) {
        this.chuongService = chuongService;
    }

    /**
     * 1. Tìm kiếm và lọc chuồng (Sử dụng Specification)
     * GET /api/chuong/search?trangThai=TRONG&maLoaiChuong=VIP
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(ChuongSearchRequest request) {
        List<ChuongDTO> list = chuongService.search(request);
        return resSuccess(list, "Tìm kiếm danh sách chuồng thành công");
    }

    /**
     * 2. Lấy tất cả chuồng (Dùng findAllDTO hiện có)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<ChuongDTO> list = chuongService.findAllDTO();
        return resSuccess(list, "Lấy toàn bộ danh sách chuồng thành công");
    }

    /**
     * 3. Lấy thông tin chi tiết một chuồng
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        ChuongDTO dto = chuongService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy thông tin chuồng: " + id);
    }

    /**
     * 4. Thêm mới chuồng (Sử dụng ChuongRequest)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ChuongRequest request) {
        ChuongDTO savedDto = chuongService.saveRequest(request);
        return resCreated(savedDto, "Thêm mới chuồng thành công");
    }

    /**
     * 5. Cập nhật thông tin chuồng
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody ChuongRequest request) {
        request.setMaChuong(id);
        ChuongDTO updatedDto = chuongService.saveRequest(request);
        return resSuccess(updatedDto, "Cập nhật thông tin chuồng thành công");
    }

    /**
     * 6. Xóa chuồng
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        chuongService.deleteById(id);
        return resSuccess(null, "Xóa chuồng thành công");
    }

    /**
     * 7. Lấy thống kê chuồng cho Dashboard
     * Trả về: tổng số, số chuồng trống, tỷ lệ lấp đầy, thống kê theo loại...
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        ChuongSummaryDTO summary = chuongService.getSummary();
        return resSuccess(summary, "Lấy số liệu thống kê chuồng thành công");

    }
}