package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.KPIThuongPhatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kpi")
@CrossOrigin("*")
public class KPIThuongPhatController extends BaseController {

    private final KPIThuongPhatService kpiService;

    public KPIThuongPhatController(KPIThuongPhatService kpiService) {
        this.kpiService = kpiService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(KPIThuongPhatSearchRequest request) {
        List<KPIThuongPhatDTO> list = kpiService.search(request);
        return resSuccess(list, "Tìm kiếm KPI thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<KPIThuongPhatDTO> list = kpiService.findAllDTO();
        return resSuccess(list, "Lấy danh sách KPI thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        KPIThuongPhatDTO dto = kpiService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy KPI");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody KPIThuongPhatRequest request) {
        KPIThuongPhatDTO saved = kpiService.saveRequest(request);
        return resCreated(saved, "Thêm KPI thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody KPIThuongPhatRequest request) {
        request.setMaKPI(id);
        KPIThuongPhatDTO updated = kpiService.saveRequest(request);
        return resSuccess(updated, "Cập nhật KPI thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        kpiService.deleteById(id);
        return resSuccess(null, "Xóa KPI thành công");
    }

    @GetMapping("/summary/{thang}")
    public ResponseEntity<Map<String, Object>> getSummary(@PathVariable String thang) {
        KPIThuongPhatSummaryDTO summary = kpiService.getSummary(thang);
        return resSuccess(summary, "Lấy thống kê KPI tháng " + thang + " thành công");
    }
}