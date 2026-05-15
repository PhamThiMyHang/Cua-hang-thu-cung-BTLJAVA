package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.HoSoNhanVienService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * HOSO_NHANVIEN CONTROLLER
 * =============================================
 * Quản lý hồ sơ chi tiết của nhân viên
 * Liên kết 1-1 với bảng NhanVien
 * Chứa thông tin: bằng cấp, kinh nghiệm, trình độ...
 */
@RestController
@RequestMapping("/api/ho-so-nhan-vien")

public class HoSoNhanVienController extends BaseController {

    private final HoSoNhanVienService hoSoNhanVienService;

    public HoSoNhanVienController(HoSoNhanVienService hoSoNhanVienService) {
        this.hoSoNhanVienService = hoSoNhanVienService;
    }

    /**
     * Tìm kiếm hồ sơ nhân viên
     * Hỗ trợ: theo mã nhân viên, từ khóa (bằng cấp, trình độ, kinh nghiệm)
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(HoSoNhanVienSearchRequest request) {
        var result = hoSoNhanVienService.search(request);
        return resSuccess(result, "Tìm kiếm hồ sơ nhân viên thành công");
    }

    /**
     * Lấy tất cả hồ sơ nhân viên
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(hoSoNhanVienService.findAllDTO(), "Lấy toàn bộ danh sách hồ sơ nhân viên thành công");
    }

    /**
     * Lấy chi tiết hồ sơ theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(hoSoNhanVienService.findByIdDTO(id), "Tìm thấy hồ sơ mã: " + id);
    }

    /**
     * Lấy hồ sơ theo mã nhân viên (API rất hay dùng)
     */
    @GetMapping("/nhan-vien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByMaNV(@PathVariable Integer maNV) {
        return resSuccess(hoSoNhanVienService.findByMaNV(maNV), 
                "Tìm thấy hồ sơ của nhân viên mã: " + maNV);
    }

    /**
     * Tạo mới hồ sơ cho nhân viên
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody HoSoNhanVienRequest request) {
        HoSoNhanVienDTO saved = hoSoNhanVienService.saveRequest(request);
        return resCreated(saved, "Tạo hồ sơ nhân viên thành công");
    }

    /**
     * Cập nhật hồ sơ nhân viên
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody HoSoNhanVienRequest request) {
        request.setMaHoSo(id);
        HoSoNhanVienDTO updated = hoSoNhanVienService.saveRequest(request);
        return resSuccess(updated, "Cập nhật hồ sơ nhân viên thành công");
    }

    /**
     * Xóa hồ sơ nhân viên
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        hoSoNhanVienService.deleteById(id);
        return resSuccess(null, "Xóa hồ sơ nhân viên thành công");
    }
}