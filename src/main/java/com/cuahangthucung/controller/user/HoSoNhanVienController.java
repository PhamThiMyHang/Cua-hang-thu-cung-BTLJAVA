package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.HoSoNhanVienService;
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
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/ho-so-nhan-vien/search?maNV=5&keyword=Đại học
     *        &sortBy=trinhDo&sortDir=asc&page=0&size=10
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            HoSoNhanVienSearchRequest request,
            @PageableDefault(size = 10, sort = "maHoSo", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<HoSoNhanVienDTO> resultPage = hoSoNhanVienService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm hồ sơ nhân viên thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(hoSoNhanVienService.findAllDTO(), "Lấy toàn bộ danh sách hồ sơ nhân viên thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(hoSoNhanVienService.findByIdDTO(id), "Tìm thấy hồ sơ mã: " + id);
    }

    /**
     * Lấy hồ sơ theo mã nhân viên (API rất hữu ích)
     */
    @GetMapping("/nhan-vien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByMaNV(@PathVariable Integer maNV) {
        HoSoNhanVienDTO hoSo = hoSoNhanVienService.findByMaNV(maNV);
        return resSuccess(hoSo, "Tìm thấy hồ sơ của nhân viên mã: " + maNV);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody HoSoNhanVienRequest request) {
        HoSoNhanVienDTO saved = hoSoNhanVienService.saveRequest(request);
        return resCreated(saved, "Tạo hồ sơ nhân viên thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody HoSoNhanVienRequest request) {
        request.setMaHoSo(id);
        HoSoNhanVienDTO updated = hoSoNhanVienService.saveRequest(request);
        return resSuccess(updated, "Cập nhật hồ sơ nhân viên thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        hoSoNhanVienService.deleteById(id);
        return resSuccess(null, "Xóa hồ sơ nhân viên thành công");
    }
}