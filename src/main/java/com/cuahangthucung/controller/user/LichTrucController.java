package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.service.user.LichTrucService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lich-truc")
@CrossOrigin("*")
public class LichTrucController extends BaseController {

    private final LichTrucService lichTrucService;

    public LichTrucController(LichTrucService lichTrucService) {
        this.lichTrucService = lichTrucService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(LichTrucSearchRequest request) {
        List<LichTrucDTO> list = lichTrucService.search(request);
        return resSuccess(list, "Tìm kiếm lịch trực thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<LichTrucDTO> list = lichTrucService.findAllDTO();
        return resSuccess(list, "Lấy danh sách lịch trực thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        LichTrucDTO dto = lichTrucService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy lịch trực");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody LichTrucRequest request) {
        LichTrucDTO saved = lichTrucService.saveRequest(request);
        return resCreated(saved, "Thêm lịch trực thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Integer id, 
                                                      @Valid @RequestBody LichTrucRequest request) {
        request.setId(id);
        LichTrucDTO updated = lichTrucService.saveRequest(request);
        return resSuccess(updated, "Cập nhật lịch trực thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        lichTrucService.deleteById(id);
        return resSuccess(null, "Xóa lịch trực thành công");
    }
}