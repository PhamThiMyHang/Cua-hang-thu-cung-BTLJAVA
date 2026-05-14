package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.service.user.LichTrucService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/lich-truc")
@CrossOrigin("*")
public class LichTrucController extends BaseController {

    private final LichTrucService lichTrucService;

    public LichTrucController(LichTrucService lichTrucService) {
        this.lichTrucService = lichTrucService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichTrucService.findAll(), "Lấy danh sách lịch trực thành công");
    }

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByNhanVien(@PathVariable Integer maNV) {
        return resSuccess(lichTrucService.findByNhanVienMaNV(maNV), "Lấy lịch trực theo nhân viên thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody LichTruc lichTruc) {
        return resCreated(lichTrucService.save(lichTruc), "Thêm lịch trực thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichTrucService.deleteById(id);
        return resSuccess(null, "Xóa lịch trực thành công");
    }
}