package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.service.pet.LoaiChuongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/loai-chuong")
@CrossOrigin("*")
public class LoaiChuongController extends BaseController {

    private final LoaiChuongService loaiChuongService;

    public LoaiChuongController(LoaiChuongService loaiChuongService) {
        this.loaiChuongService = loaiChuongService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(loaiChuongService.findAll(), "Lấy danh sách loại chuồng thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody LoaiChuong loaiChuong) {
        return resCreated(loaiChuongService.save(loaiChuong), "Thêm loại chuồng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        loaiChuongService.deleteById(id);
        return resSuccess(null, "Xóa loại chuồng thành công");
    }
}