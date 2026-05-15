package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichSuDangNhapService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lich-su-dang-nhap")
@CrossOrigin("*")
public class LichSuDangNhapController extends BaseController {

    private final LichSuDangNhapService lichSuDangNhapService;

    public LichSuDangNhapController(LichSuDangNhapService lichSuDangNhapService) {
        this.lichSuDangNhapService = lichSuDangNhapService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(LichSuDangNhapSearchRequest request) {
        List<LichSuDangNhapDTO> list = lichSuDangNhapService.search(request);
        return resSuccess(list, "Tìm kiếm lịch sử đăng nhập thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<LichSuDangNhapDTO> list = lichSuDangNhapService.findAllDTO();
        return resSuccess(list, "Lấy danh sách lịch sử đăng nhập thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        LichSuDangNhapDTO dto = lichSuDangNhapService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy lịch sử đăng nhập");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LichSuDangNhapRequest request) {
        LichSuDangNhapDTO saved = lichSuDangNhapService.saveRequest(request);
        return resCreated(saved, "Thêm lịch sử đăng nhập thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichSuDangNhapService.deleteById(id);
        return resSuccess(null, "Xóa lịch sử đăng nhập thành công");
    }
}