package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichSuDangNhapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * LICHSU_DANGNHAP CONTROLLER
 * =============================================
 * Quản lý lịch sử đăng nhập / đăng xuất của người dùng
 * Liên kết với bảng: User
 * Đây là bảng log (audit trail), dùng để theo dõi hoạt động hệ thống
 */
@RestController
@RequestMapping("/api/lich-su-dang-nhap")

public class LichSuDangNhapController extends BaseController {

    private final LichSuDangNhapService lichSuDangNhapService;

    public LichSuDangNhapController(LichSuDangNhapService lichSuDangNhapService) {
        this.lichSuDangNhapService = lichSuDangNhapService;
    }

    /**
     * Tìm kiếm lịch sử đăng nhập
     * Hỗ trợ lọc theo: userID, username, trạng thái (SUCCESS/FAIL), khoảng thời gian
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(LichSuDangNhapSearchRequest request) {
        var result = lichSuDangNhapService.search(request);
        return resSuccess(result, "Tìm kiếm lịch sử đăng nhập thành công");
    }

    /**
     * Lấy tất cả lịch sử đăng nhập
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichSuDangNhapService.findAllDTO(), "Lấy toàn bộ lịch sử đăng nhập thành công");
    }

    /**
     * Lấy chi tiết một bản ghi lịch sử theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(lichSuDangNhapService.findByIdDTO(id), "Tìm thấy lịch sử đăng nhập mã: " + id);
    }

    /**
     * Ghi nhận lịch sử đăng nhập
     * Thường được gọi tự động từ hệ thống Authentication
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody LichSuDangNhapRequest request) {
        LichSuDangNhapDTO saved = lichSuDangNhapService.saveRequest(request);
        return resCreated(saved, "Ghi nhận lịch sử đăng nhập thành công");
    }

    /**
     * Xóa một bản ghi lịch sử (dùng để dọn dẹp dữ liệu cũ)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichSuDangNhapService.deleteById(id);
        return resSuccess(null, "Xóa lịch sử đăng nhập thành công");
    }
}