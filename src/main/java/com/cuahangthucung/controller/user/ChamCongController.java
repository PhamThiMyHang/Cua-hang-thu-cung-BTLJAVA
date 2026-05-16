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
     * Tìm kiếm và lọc bản ghi chấm công
     * Hỗ trợ lọc theo: mã nhân viên, ngày chấm công, khoảng thời gian (tuNgay → denNgay)
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            ChamCongSearchRequest request,
            @PageableDefault(sort = "ngay", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 1. Lấy kết quả phân trang từ Service
        Page<ChamCong> resultPage = chamCongService.findAll(
                ChamCongSpecification.getFilter(request),
                pageable
        );

        // 2. Map sang DTO và trả về (Cấu trúc chuẩn giống PetController)
        return resSuccess(resultPage.map(chamCongService::convertToDTO), "Tìm kiếm chấm công thành công");
    }

    /**
     * Lấy tất cả bản ghi chấm công trong hệ thống
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(chamCongService.findAllDTO(), "Lấy toàn bộ danh sách chấm công thành công");
    }

    /**
     * Lấy chi tiết một bản ghi chấm công theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(chamCongService.findByIdDTO(id), "Tìm thấy bản ghi chấm công mã: " + id);
    }

    /**
     * Thêm mới bản ghi chấm công (gắn với nhân viên)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ChamCongRequest request) {
        ChamCongDTO saved = chamCongService.saveRequest(request);
        return resCreated(saved, "Chấm công thành công");
    }

    /**
     * Cập nhật thông tin chấm công (sửa giờ vào/ra)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ChamCongRequest request) {
        request.setMaCC(id);
        ChamCongDTO updated = chamCongService.saveRequest(request);
        return resSuccess(updated, "Cập nhật chấm công thành công");
    }

    /**
     * Xóa một bản ghi chấm công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        chamCongService.deleteById(id);
        return resSuccess(null, "Xóa bản ghi chấm công thành công");
    }

    /**
     * Thống kê chấm công (tổng số ngày, số ngày đi làm, tỷ lệ...)
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(ChamCongSearchRequest request) {
        ChamCongSummaryDTO summary = chamCongService.getSummary(request);
        return resSuccess(summary, "Lấy thống kê chấm công thành công");
    }
}