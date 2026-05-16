package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichTrucService;
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
 * LICHTRUC CONTROLLER
 * =============================================
 * Quản lý lịch trực nhân viên
 * Liên kết với bảng: NhanVien
 * Có kiểm tra trùng lịch (1 nhân viên không trực 2 ca cùng ngày)
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
     * TÌM KIẾM + LỌC + PHÂN TRANG + SẮP XẾP ĐỘNG
     *
     * Ví dụ: GET /api/lich-truc/search?maNV=5&ngay=2025-05-16&caLamViec=SANG
     *        &tuNgay=2025-05-01&denNgay=2025-05-31&sortBy=ngay&sortDir=desc
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            LichTrucSearchRequest request,
            @PageableDefault(size = 10, sort = "ngay", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<LichTrucDTO> resultPage = lichTrucService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm lịch trực thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichTrucService.findAllDTO(), "Lấy toàn bộ danh sách lịch trực thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return resSuccess(lichTrucService.findByIdDTO(id), "Tìm thấy lịch trực mã: " + id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LichTrucRequest request) {
        LichTrucDTO saved = lichTrucService.saveRequest(request);
        return resCreated(saved, "Tạo lịch trực thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody LichTrucRequest request) {
        request.setId(id);
        LichTrucDTO updated = lichTrucService.saveRequest(request);
        return resSuccess(updated, "Cập nhật lịch trực thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichTrucService.deleteById(id);
        return resSuccess(null, "Xóa lịch trực thành công");
    }
}