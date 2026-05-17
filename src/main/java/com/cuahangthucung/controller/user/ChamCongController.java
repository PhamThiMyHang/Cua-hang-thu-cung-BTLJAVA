package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.repository.user.ChamCongSpecification;
import com.cuahangthucung.service.user.ChamCongService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * CHAMCONG CONTROLLER
 * =============================================
 * Quản lý chấm công nhân viên
 * Liên kết với bảng: NhanVien
 * Chức năng: ghi nhận giờ vào/ra, tính số giờ làm, thống kê đi làm
 */
@RestController
@RequestMapping("/api/cham-cong")

public class ChamCongController extends BaseController {

    private final ChamCongService chamCongService;

    public ChamCongController(ChamCongService chamCongService) {
        this.chamCongService = chamCongService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/cham-cong/search?maNV=5&tuNgay=2025-01-01&denNgay=2025-01-31
     *        &sortBy=ngay&sortDir=desc&page=0&size=15
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            ChamCongSearchRequest request,
            @PageableDefault(sort = "ngay", direction = Sort.Direction.DESC) Pageable pageable
    ) {Page<ChamCongDTO> resultPage = chamCongService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm chấm công thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(chamCongService.findAllDTO(), "Lấy toàn bộ danh sách chấm công thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(chamCongService.findByIdDTO(id), "Tìm thấy bản ghi chấm công mã: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ChamCongRequest request) {
        ChamCongDTO saved = chamCongService.saveRequest(request);
        return resCreated(saved, "Chấm công thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ChamCongRequest request) {
        request.setMaCC(id);
        ChamCongDTO updated = chamCongService.saveRequest(request);
        return resSuccess(updated, "Cập nhật chấm công thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        chamCongService.deleteById(id);
        return resSuccess(null, "Xóa bản ghi chấm công thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(ChamCongSearchRequest request) {
        ChamCongSummaryDTO summary = chamCongService.getSummary(request);
        return resSuccess(summary, "Lấy thống kê chấm công thành công");
    }
}