package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.HoSoNhanVienService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ho-so-nhan-vien")
@CrossOrigin("*")
public class HoSoNhanVienController extends BaseController {

    private final HoSoNhanVienService hoSoNhanVienService;

    public HoSoNhanVienController(HoSoNhanVienService hoSoNhanVienService) {
        this.hoSoNhanVienService = hoSoNhanVienService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(HoSoNhanVienSearchRequest request) {
        List<HoSoNhanVienDTO> list = hoSoNhanVienService.search(request);
        return resSuccess(list, "Tìm kiếm hồ sơ thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<HoSoNhanVienDTO> list = hoSoNhanVienService.findAllDTO();
        return resSuccess(list, "Lấy danh sách hồ sơ thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        HoSoNhanVienDTO dto = hoSoNhanVienService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy hồ sơ");
    }

    @GetMapping("/nhan-vien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByMaNV(@PathVariable Integer maNV) {
        HoSoNhanVienDTO dto = hoSoNhanVienService.findByMaNV(maNV);
        return resSuccess(dto, "Tìm thấy hồ sơ nhân viên");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody HoSoNhanVienRequest request) {
        HoSoNhanVienDTO saved = hoSoNhanVienService.saveRequest(request);
        return resCreated(saved, "Thêm hồ sơ thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody HoSoNhanVienRequest request) {
        request.setMaHoSo(id);
        HoSoNhanVienDTO updated = hoSoNhanVienService.saveRequest(request);
        return resSuccess(updated, "Cập nhật hồ sơ thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        hoSoNhanVienService.deleteById(id);
        return resSuccess(null, "Xóa hồ sơ thành công");
    }
}