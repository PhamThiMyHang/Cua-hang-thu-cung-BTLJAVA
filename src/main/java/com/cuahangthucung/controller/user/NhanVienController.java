package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nhan-vien")
@CrossOrigin("*")
public class NhanVienController extends BaseController {

    private final NhanVienService nhanVienService;

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(NhanVienSearchRequest request) {
        List<NhanVienDTO> list = nhanVienService.search(request);
        return resSuccess(list, "Tìm kiếm nhân viên thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<NhanVienDTO> list = nhanVienService.findAllDTO();
        return resSuccess(list, "Lấy danh sách nhân viên thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        NhanVienDTO dto = nhanVienService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy nhân viên");
    }
/* bỏ
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody NhanVienRequest request) {
        NhanVienDTO saved = nhanVienService.saveRequest(request);
        return resCreated(saved, "Thêm nhân viên thành công");
    }
*/
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody NhanVien nhanVien) {
        // KHÔNG cần set MaNV thủ công, DB sẽ tự tăng
        NhanVien saved = nhanVienService.save(nhanVien);
        return resCreated(saved, "Thêm nhân viên thành công với ID: " + saved.getMaNV());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody NhanVienRequest request) {
        request.setMaNV(id);
        NhanVienDTO updated = nhanVienService.saveRequest(request);
        return resSuccess(updated, "Cập nhật nhân viên thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        nhanVienService.deleteById(id);
        return resSuccess(null, "Xóa nhân viên thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        NhanVienSummaryDTO summary = nhanVienService.getSummary();
        return resSuccess(summary, "Lấy thống kê nhân viên thành công");
    }
}