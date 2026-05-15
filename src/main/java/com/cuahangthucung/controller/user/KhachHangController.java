package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/khach-hang")
@CrossOrigin("*")
public class KhachHangController extends BaseController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(KhachHangSearchRequest request) {
        List<KhachHangDTO> list = khachHangService.search(request);
        return resSuccess(list, "Tìm kiếm khách hàng thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<KhachHangDTO> list = khachHangService.findAllDTO();
        return resSuccess(list, "Lấy danh sách khách hàng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        KhachHangDTO dto = khachHangService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy khách hàng");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KhachHangRequest request) {
        KhachHangDTO saved = khachHangService.saveRequest(request);
        return resCreated(saved, "Thêm khách hàng thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody KhachHangRequest request) {
        request.setMaKH(id);
        KhachHangDTO updated = khachHangService.saveRequest(request);
        return resSuccess(updated, "Cập nhật khách hàng thành công");
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