package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.pet.LichSuSearchRequest;
import com.cuahangthucung.dto.pet.LichSuSucKhoeRequest;
import com.cuahangthucung.service.pet.LichSuSucKhoeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lich-su-suc-khoe")
@CrossOrigin("*")
public class LichSuSucKhoeController extends BaseController {

    private final LichSuSucKhoeService service;

    public LichSuSucKhoeController(LichSuSucKhoeService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(LichSuSearchRequest request) {
        return resSuccess(service.search(request), "Tìm kiếm lịch sử thành công");
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody LichSuSucKhoeRequest request) {
        return resCreated(service.saveRequest(request), "Thêm lịch sử thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return resSuccess(null, "Xóa lịch sử thành công");
    }
}