package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * KHACHHANG CONTROLLER
 * =============================================
 * Quản lý thông tin Khách hàng
 * Liên kết với: User (tài khoản), Pet (thú cưng)
 * Chức năng quan trọng: quản lý điểm tích lũy, phân loại khách VIP/Thường
 */
@RestController
@RequestMapping("/api/khach-hang")

public class KhachHangController extends BaseController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    /**
     * Tìm kiếm khách hàng theo nhiều tiêu chí
     * Hỗ trợ: tên, số điện thoại, loại khách hàng (VIP, THƯỜNG, SI, LE), điểm tích lũy
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(KhachHangSearchRequest request) {
        var result = khachHangService.search(request);
        return resSuccess(result, "Tìm kiếm khách hàng thành công");
    }

    /**
     * Lấy toàn bộ danh sách khách hàng
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(khachHangService.findAllDTO(), "Lấy toàn bộ danh sách khách hàng thành công");
    }

    /**
     * Lấy thông tin chi tiết một khách hàng
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(khachHangService.findByIdDTO(id), "Tìm thấy khách hàng mã: " + id);
    }

    /**
     * Thêm mới khách hàng
     * Có thể liên kết với UserID nếu khách hàng có tài khoản
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KhachHangRequest request) {
        KhachHangDTO saved = khachHangService.saveRequest(request);
        return resCreated(saved, "Thêm khách hàng mới thành công");
    }

    /**
     * Cập nhật thông tin khách hàng
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody KhachHangRequest request) {
        request.setMaKH(id);
        KhachHangDTO updated = khachHangService.saveRequest(request);
        return resSuccess(updated, "Cập nhật thông tin khách hàng thành công");
    }

    /**
     * Xóa khách hàng
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        khachHangService.deleteById(id);
        return resSuccess(null, "Xóa khách hàng thành công");
    }

    /**
     * Thống kê khách hàng cho Dashboard
     * Bao gồm: tổng số KH, số VIP, số THƯỜNG, tổng điểm tích lũy
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        KhachHangSummaryDTO summary = khachHangService.getSummary();
        return resSuccess(summary, "Lấy thống kê khách hàng thành công");
    }
}