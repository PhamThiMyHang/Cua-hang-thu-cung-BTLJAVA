package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichTrucService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * LICHTRUC CONTROLLER
 * =============================================
 * Quản lý lịch trực nhân viên
 * Liên kết với bảng: NhanVien
 * Có kiểm tra trùng lịch (một nhân viên không thể trực 2 ca cùng ngày)
 */
@RestController
@RequestMapping("/api/lich-truc")
@CrossOrigin("*")
public class LichTrucController extends BaseController {

    private final LichTrucService lichTrucService;

    public LichTrucController(LichTrucService lichTrucService) {
        this.lichTrucService = lichTrucService;
    }

    /**
     * Tìm kiếm lịch trực
     * Hỗ trợ: mã nhân viên, ngày, ca làm việc, khoảng thời gian
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(LichTrucSearchRequest request) {
        var result = lichTrucService.search(request);
        return resSuccess(result, "Tìm kiếm lịch trực thành công");
    }

    /**
     * Lấy tất cả lịch trực
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichTrucService.findAllDTO(), "Lấy toàn bộ lịch trực thành công");
    }

    /**
     * Lấy chi tiết một lịch trực theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(lichTrucService.findByIdDTO(id), "Tìm thấy lịch trực mã: " + id);
    }

    /**
     * Tạo mới lịch trực cho nhân viên
     * Service sẽ kiểm tra trùng ca trước khi lưu
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LichTrucRequest request) {
        LichTrucDTO saved = lichTrucService.saveRequest(request);
        return resCreated(saved, "Tạo lịch trực thành công");
    }

    /**
     * Cập nhật lịch trực
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody LichTrucRequest request) {
        request.setId(id);
        LichTrucDTO updated = lichTrucService.saveRequest(request);
        return resSuccess(updated, "Cập nhật lịch trực thành công");
    }

    /**
     * Xóa lịch trực
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichTrucService.deleteById(id);
        return resSuccess(null, "Xóa lịch trực thành công");
    }
}