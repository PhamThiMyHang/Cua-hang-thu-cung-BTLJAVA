package com.cuahangthucung.controller.use;


import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.use.lichhen.LichHenDTO;
import com.cuahangthucung.dto.use.lichhen.LichHenRequest;
import com.cuahangthucung.dto.use.lichhen.LichHenSearchRequest;
import com.cuahangthucung.service.use.service.LichHenService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/lich-hen")
//@CrossOrigin("*")
public class LichHenController extends BaseController {

    private final LichHenService lichHenService;

    public LichHenController(LichHenService lichHenService) {
        this.lichHenService = lichHenService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            LichHenSearchRequest request,
            @PageableDefault(sort = "thoiGian", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<LichHenDTO> result = lichHenService.search(request, pageable);
        return resSuccess(result, "Tìm kiếm lịch hẹn thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(lichHenService.findAllDTO(), "Lấy danh sách lịch hẹn thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return resSuccess(lichHenService.findByIdDTO(id), "Lấy thông tin lịch hẹn thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LichHenRequest request) {
        LichHenDTO saved = lichHenService.saveRequest(request);
        return resCreated(saved, "Tạo lịch hẹn thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody LichHenRequest request) {
        request.setMaLich(id);
        LichHenDTO updated = lichHenService.saveRequest(request);
        return resSuccess(updated, "Cập nhật lịch hẹn thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        lichHenService.deleteById(id);
        return resSuccess(null, "Xóa lịch hẹn thành công");
    }
}