package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.service.user.ChamCongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/cham-cong")
@CrossOrigin("*")
public class ChamCongController extends BaseController {

    private final ChamCongService chamCongService;

    public ChamCongController(ChamCongService chamCongService) {
        this.chamCongService = chamCongService;
    }

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByNhanVien(@PathVariable Integer maNV) {
        return resSuccess(chamCongService.findByNhanVienMaNV(maNV), "Lấy chấm công thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody ChamCong chamCong) {
        return resCreated(chamCongService.save(chamCong), "Chấm công thành công");
    }
}