package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KPIThuongPhatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * KPI_THUONGPHAT CONTROLLER
 * =============================================
 * Quản lý KPI, Thưởng - Phạt nhân viên
 * Liên kết với bảng: NhanVien
 * Dùng để đánh giá hiệu suất và tính lương cho nhân viên
 */
@RestController
@RequestMapping("/api/kpi-thuong-phat")

public class KPIThuongPhatController extends BaseController {

    private final KPIThuongPhatService kpiService;

    public KPIThuongPhatController(KPIThuongPhatService kpiService) {
        this.kpiService = kpiService;
    }

    /**
     * Tìm kiếm KPI theo nhân viên và tháng
     * Hỗ trợ: mã NV, tháng (YYYY-MM), keyword
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(KPIThuongPhatSearchRequest request) {
        var result = kpiService.search(request);
        return resSuccess(result, "Tìm kiếm KPI thành công");
    }

    /**
     * Lấy tất cả các bản ghi KPI
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(kpiService.findAllDTO(), "Lấy toàn bộ danh sách KPI thành công");
    }

    /**
     * Lấy chi tiết một bản ghi KPI theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(kpiService.findByIdDTO(id), "Tìm thấy KPI mã: " + id);
    }

    /**
     * Thêm mới KPI thưởng/phạt cho nhân viên
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KPIThuongPhatRequest request) {
        KPIThuongPhatDTO saved = kpiService.saveRequest(request);
        return resCreated(saved, "Thêm KPI thành công");
    }

    /**
     * Cập nhật thông tin KPI
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody KPIThuongPhatRequest request) {
        request.setMaKPI(id);
        KPIThuongPhatDTO updated = kpiService.saveRequest(request);
        return resSuccess(updated, "Cập nhật KPI thành công");
    }

    /**
     * Xóa một bản ghi KPI
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        kpiService.deleteById(id);
        return resSuccess(null, "Xóa KPI thành công");
    }

    /**
     * Thống kê KPI theo tháng (tổng thưởng, tổng phạt, kết quả)
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(@RequestParam(required = false) String thang) {
        KPIThuongPhatSummaryDTO summary = kpiService.getSummary(thang);
        return resSuccess(summary, "Lấy thống kê KPI theo tháng thành công");
    }
}