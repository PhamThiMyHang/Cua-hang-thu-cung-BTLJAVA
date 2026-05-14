package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.service.user.HoSoNhanVienService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hoso-nhanvien")
@CrossOrigin("*")
public class HoSoNhanVienController extends BaseController {

    private final HoSoNhanVienService hoSoNhanVienService;

    public HoSoNhanVienController(HoSoNhanVienService hoSoNhanVienService) {
        this.hoSoNhanVienService = hoSoNhanVienService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(hoSoNhanVienService.findAll(), "Lấy danh sách hồ sơ nhân viên thành công");
    }

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByNhanVien(@PathVariable Integer maNV) {
        return hoSoNhanVienService.findByNhanVienMaNV(maNV)
                .map(hs -> resSuccess(hs, "Tìm thấy hồ sơ"))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody HoSoNhanVien hoSo) {
        return resCreated(hoSoNhanVienService.save(hoSo), "Thêm hồ sơ nhân viên thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, @RequestBody HoSoNhanVien hoSo) {
        HoSoNhanVien updated = hoSoNhanVienService.update(id, hoSo);
        return resSuccess(updated, "Cập nhật hồ sơ thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        hoSoNhanVienService.deleteById(id);
        return resSuccess(null, "Xóa hồ sơ thành công");
    }
}