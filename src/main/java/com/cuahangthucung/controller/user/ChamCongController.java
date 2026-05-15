package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.ChamCongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cham-cong")
@CrossOrigin("*")
public class ChamCongController extends BaseController {

    private final ChamCongService chamCongService;

    public ChamCongController(ChamCongService chamCongService) {
        this.chamCongService = chamCongService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(ChamCongSearchRequest request) {
        List<ChamCongDTO> list = chamCongService.search(request);
        return resSuccess(list, "Tìm kiếm chấm công thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<ChamCongDTO> list = chamCongService.findAllDTO();
        return resSuccess(list, "Lấy danh sách chấm công thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        ChamCongDTO dto = chamCongService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy bản ghi chấm công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ChamCongRequest request) {
        ChamCongDTO saved = chamCongService.saveRequest(request);
        return resCreated(saved, "Chấm công thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody ChamCongRequest request) {
        request.setMaCC(id);
        ChamCongDTO updated = chamCongService.saveRequest(request);
        return resSuccess(updated, "Cập nhật chấm công thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        chamCongService.deleteById(id);
        return resSuccess(null, "Xóa bản ghi chấm công thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(ChamCongSearchRequest request) {
        ChamCongSummaryDTO summary = chamCongService.getSummary(request);
        return resSuccess(summary, "Lấy thống kê chấm công thành công");
    }
}