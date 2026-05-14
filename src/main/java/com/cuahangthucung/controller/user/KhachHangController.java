package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.service.user.KhachHangService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/khachhang")
@CrossOrigin("*")
public class KhachHangController extends BaseController {

    private final KhachHangService khachHangService;

    public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(khachHangService.findAll(), "Lấy danh sách khách hàng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return khachHangService.findById(id)
                .map(kh -> resSuccess(kh, "Tìm thấy khách hàng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getByUserId(@PathVariable Integer userId) {
        return khachHangService.findByUserUserID(userId)
                .map(kh -> resSuccess(kh, "Tìm thấy khách hàng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sdt/{sdt}")
    public ResponseEntity<Map<String, Object>> getBySdt(@PathVariable String sdt) {
        return khachHangService.findBySdt(sdt)
                .map(kh -> resSuccess(kh, "Tìm thấy khách hàng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody KhachHang khachHang) {
        KhachHang saved = khachHangService.save(khachHang);
        return resCreated(saved, "Thêm khách hàng thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody KhachHang khachHang) {
        KhachHang updated = khachHangService.update(id, khachHang);
        return resSuccess(updated, "Cập nhật khách hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        khachHangService.deleteById(id);
        return resSuccess(null, "Xóa khách hàng thành công");
    }
}