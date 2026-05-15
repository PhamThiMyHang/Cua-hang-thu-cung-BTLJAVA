package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.NhanVienService;
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
 * NHÂN VIÊN CONTROLLER
 * =============================================
 * Quản lý thông tin nhân viên
 * Liên kết với: User, HoSoNhanVien, ChamCong, LichTruc, KPIThuongPhat
 */
@RestController
@RequestMapping("/api/nhan-vien")
public class NhanVienController extends BaseController {

    private final NhanVienService nhanVienService;

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    /**
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ gọi API:
     * GET /api/nhan-vien/search?tenNV=Nguyễn&chucVu=STAFF&keyword=admin
     *     &sortBy=tenNV&sortDir=asc&page=0&size=10
     *
     * Hỗ trợ lọc theo: tenNV, sdt, chucVu, keyword
     * Hỗ trợ sắp xếp động theo bất kỳ trường nào
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            NhanVienSearchRequest request,
            @PageableDefault(size = 10, sort = "maNV", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<NhanVienDTO> resultPage = nhanVienService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm và lọc nhân viên thành công");
    }

    /**
     * Lấy tất cả nhân viên (không phân trang) - Dùng khi cần danh sách ngắn
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(nhanVienService.findAllDTO(), "Lấy toàn bộ danh sách nhân viên thành công");
    }

    /**
     * Lấy chi tiết một nhân viên theo mã
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(nhanVienService.findByIdDTO(id), "Lấy thông tin nhân viên thành công");
    }

    /**
     * Tạo mới nhân viên
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody NhanVienRequest request) {
        NhanVienDTO saved = nhanVienService.saveRequest(request);
        return resCreated(saved, "Thêm nhân viên mới thành công");
    }

    /**
     * Cập nhật nhân viên
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody NhanVienRequest request) {
        request.setMaNV(id);
        NhanVienDTO updated = nhanVienService.saveRequest(request);
        return resSuccess(updated, "Cập nhật nhân viên thành công");
    }

    /**
     * Xóa nhân viên
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        nhanVienService.deleteById(id);
        return resSuccess(null, "Xóa nhân viên thành công");
    }

    /**
     * Thống kê Dashboard
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        NhanVienSummaryDTO summary = nhanVienService.getSummary();
        return resSuccess(summary, "Lấy thống kê nhân viên thành công");
    }
}