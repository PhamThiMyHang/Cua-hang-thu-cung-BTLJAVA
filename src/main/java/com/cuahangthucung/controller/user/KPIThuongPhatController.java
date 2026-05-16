package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KPIThuongPhatService;
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
 * KPI_THUONGPHAT CONTROLLER
 * =============================================
 * Quản lý KPI, Thưởng - Phạt nhân viên
 * Liên kết với bảng: NhanVien
 * Dùng để đánh giá hiệu suất và tính lương
 */
@RestController
@RequestMapping("/api/kpi-thuong-phat")

public class KPIThuongPhatController extends BaseController {

    private final KPIThuongPhatService kpiService;

    public KPIThuongPhatController(KPIThuongPhatService kpiService) {
        this.kpiService = kpiService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/kpi-thuong-phat/search?maNV=5&thang=2025-05&keyword=Nguyễn
     *        &sortBy=thang&sortDir=desc&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            KPIThuongPhatSearchRequest request,
            @PageableDefault(size = 10, sort = "thang", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<KPIThuongPhatDTO> resultPage = kpiService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm KPI thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(kpiService.findAllDTO(), "Lấy toàn bộ danh sách KPI thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(kpiService.findByIdDTO(id), "Tìm thấy KPI mã: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KPIThuongPhatRequest request) {
        KPIThuongPhatDTO saved = kpiService.saveRequest(request);
        return resCreated(saved, "Thêm KPI thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody KPIThuongPhatRequest request) {
        request.setMaKPI(id);
        KPIThuongPhatDTO updated = kpiService.saveRequest(request);
        return resSuccess(updated, "Cập nhật KPI thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        kpiService.deleteById(id);
        return resSuccess(null, "Xóa KPI thành công");
    }

    /**
     * Thống kê KPI theo tháng
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(@RequestParam(required = false) String thang) {
        KPIThuongPhatSummaryDTO summary = kpiService.getSummary(thang);
        return resSuccess(summary, "Lấy thống kê KPI theo tháng thành công");
    }
}