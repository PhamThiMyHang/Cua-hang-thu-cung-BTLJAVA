package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichSuDangNhapService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * LICHSU_DANGNHAP CONTROLLER
 * =============================================
 * Quản lý lịch sử đăng nhập / đăng xuất của người dùng
 * Liên kết với bảng: User
 * Đây là bảng log (audit trail)
 */
@RestController
@RequestMapping("/api/lich-su-dang-nhap")

public class LichSuDangNhapController extends BaseController {

    private final LichSuDangNhapService lichSuDangNhapService;

    public LichSuDangNhapController(LichSuDangNhapService lichSuDangNhapService) {
        this.lichSuDangNhapService = lichSuDangNhapService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/lich-su-dang-nhap/search?userID=1&trangThai=SUCCESS&username=admin
     *        &tuNgay=2025-05-01T00:00&denNgay=2025-05-16T23:59&sortBy=thoiGian&sortDir=desc
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            LichSuDangNhapSearchRequest request,
            @PageableDefault(size = 15, sort = "thoiGian", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<LichSuDangNhapDTO> resultPage = lichSuDangNhapService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm lịch sử đăng nhập thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichSuDangNhapService.findAllDTO(), "Lấy toàn bộ lịch sử đăng nhập thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(lichSuDangNhapService.findByIdDTO(id), "Tìm thấy lịch sử đăng nhập mã: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody LichSuDangNhapRequest request) {
        LichSuDangNhapDTO saved = lichSuDangNhapService.saveRequest(request);
        return resCreated(saved, "Ghi nhận lịch sử đăng nhập thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichSuDangNhapService.deleteById(id);
        return resSuccess(null, "Xóa lịch sử đăng nhập thành công");
    }
}