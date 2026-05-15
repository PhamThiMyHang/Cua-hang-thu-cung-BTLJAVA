package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KhachHangService;
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
 * KHACHHANG CONTROLLER
 * =============================================
 * Quản lý thông tin Khách hàng
 * Liên kết với: User (tài khoản)
 * Chức năng: quản lý điểm tích lũy, phân loại VIP/Thường...
 */
@RestController
@RequestMapping("/api/khach-hang")

public class KhachHangController extends BaseController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/khach-hang/search?tenKH=Nguyễn&sdt=0123&loaiKH=VIP&keyword=abc
     *        &sortBy=tenKH&sortDir=asc&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            KhachHangSearchRequest request,
            @PageableDefault(size = 10, sort = "maKH", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<KhachHangDTO> resultPage = khachHangService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm khách hàng thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(khachHangService.findAllDTO(), "Lấy toàn bộ danh sách khách hàng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(khachHangService.findByIdDTO(id), "Tìm thấy khách hàng mã: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KhachHangRequest request) {
        KhachHangDTO saved = khachHangService.saveRequest(request);
        return resCreated(saved, "Thêm khách hàng mới thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody KhachHangRequest request) {
        request.setMaKH(id);
        KhachHangDTO updated = khachHangService.saveRequest(request);
        return resSuccess(updated, "Cập nhật thông tin khách hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        khachHangService.deleteById(id);
        return resSuccess(null, "Xóa khách hàng thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        KhachHangSummaryDTO summary = khachHangService.getSummary();
        return resSuccess(summary, "Lấy thống kê khách hàng thành công");
    }
}