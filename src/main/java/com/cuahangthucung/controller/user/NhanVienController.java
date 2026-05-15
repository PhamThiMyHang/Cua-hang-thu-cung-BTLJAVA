package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.service.user.NhanVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/nhanvien")
@CrossOrigin("*")
public class NhanVienController extends BaseController {

    private final NhanVienService nhanVienService;

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(nhanVienService.findAll(), "Lấy danh sách nhân viên thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        return nhanVienService.findById(id)
                .map(nv -> resSuccess(nv, "Tìm thấy nhân viên"))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getByUserId(@PathVariable Integer userId) {
        return nhanVienService.findByUserUserID(userId)
                .map(nv -> resSuccess(nv, "Tìm thấy nhân viên"))
                .orElse(ResponseEntity.notFound().build());
    }
/* bỏ
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody NhanVien nhanVien) {
        NhanVien saved = nhanVienService.save(nhanVien);
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
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody NhanVien nhanVien) {
        NhanVien updated = nhanVienService.update(id, nhanVien);
        return resSuccess(updated, "Cập nhật nhân viên thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        nhanVienService.deleteById(id);
        return resSuccess(null, "Xóa nhân viên thành công");
    }
}