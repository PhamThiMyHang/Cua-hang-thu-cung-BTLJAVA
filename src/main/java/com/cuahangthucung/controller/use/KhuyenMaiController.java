package com.cuahangthucung.controller.use;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiDTO;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiRequest;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiSearchRequest;
import com.cuahangthucung.service.use.service.KhuyenMaiService; // Đheader ĐÃ SỬA: Import đúng package thư mục chứa interface
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/khuyen-mai")
//@CrossOrigin("*")
public class KhuyenMaiController extends BaseController {

    private final KhuyenMaiService khuyenMaiService;

    public KhuyenMaiController(KhuyenMaiService khuyenMaiService) {
        this.khuyenMaiService = khuyenMaiService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            KhuyenMaiSearchRequest request,
            @PageableDefault(sort = "ngayBD", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<KhuyenMaiDTO> result = khuyenMaiService.search(request, pageable);
        return resSuccess(result, "Tìm kiếm khuyến mãi thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(khuyenMaiService.findAllDTO(), "Lấy danh sách khuyến mãi thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return resSuccess(khuyenMaiService.findByIdDTO(id), "Lấy thông tin KM thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KhuyenMaiRequest request) {
        KhuyenMaiDTO saved = khuyenMaiService.saveRequest(request);
        return resCreated(saved, "Thêm khuyến mãi thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody KhuyenMaiRequest request) {
        request.setMaKM(id);
        KhuyenMaiDTO updated = khuyenMaiService.saveRequest(request);
        return resSuccess(updated, "Cập nhật khuyến mãi thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        khuyenMaiService.deleteById(id);
        return resSuccess(null, "Xóa khuyến mãi thành công");
    }
}