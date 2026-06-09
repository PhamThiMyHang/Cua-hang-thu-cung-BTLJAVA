package com.cuahangthucung.controller.use;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.use.sanpham.SanPhamDTO;
import com.cuahangthucung.dto.use.sanpham.SanPhamRequest;
import com.cuahangthucung.dto.use.sanpham.SanPhamSearchRequest;
import com.cuahangthucung.service.use.service.SanPhamService;
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
 * SANPHAM CONTROLLER
 * =============================================
 * Quản lý sản phẩm / hàng hóa trong kho
 */
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController extends BaseController {

    private final SanPhamService sanPhamService;

    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            SanPhamSearchRequest request,
            @PageableDefault(size = 10, sort = "maSP", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<SanPhamDTO> resultPage = sanPhamService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm sản phẩm thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(sanPhamService.findAllDTO(), "Lấy danh sách sản phẩm thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return resSuccess(sanPhamService.findByIdDTO(id), "Lấy thông tin sản phẩm thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody SanPhamRequest request) {
        SanPhamDTO saved = sanPhamService.saveRequest(request);
        return resCreated(saved, "Thêm sản phẩm mới thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody SanPhamRequest request) {
        request.setMaSP(id);
        SanPhamDTO updated = sanPhamService.saveRequest(request);
        return resSuccess(updated, "Cập nhật sản phẩm thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        sanPhamService.deleteById(id);
        return resSuccess(null, "Xóa sản phẩm thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return resSuccess(sanPhamService.getSummary(5), "Lấy thống kê sản phẩm thành công");
    }

    @GetMapping("/vi-tri/{maViTri}/count")
    public ResponseEntity<Map<String, Object>> countByViTri(
            @PathVariable String maViTri) {

        return resSuccess(
                sanPhamService.countByViTri(maViTri),
                "Lấy số lượng sản phẩm theo kệ thành công"
        );
    }
}