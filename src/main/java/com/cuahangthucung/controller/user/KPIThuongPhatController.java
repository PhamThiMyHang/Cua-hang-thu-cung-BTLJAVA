package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.service.user.KPIThuongPhatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/kpi")
@CrossOrigin("*")
public class KPIThuongPhatController extends BaseController {

    private final KPIThuongPhatService kpiService;

    public KPIThuongPhatController(KPIThuongPhatService kpiService) {
        this.kpiService = kpiService;
    }

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<Map<String, Object>> getByNhanVien(@PathVariable Integer maNV) {
        return resSuccess(kpiService.findByNhanVienMaNV(maNV), "Lấy KPI thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody KPIThuongPhat kpi) {
        return resCreated(kpiService.save(kpi), "Thêm KPI thành công");
    }
}