package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.service.pet.ChuongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chuong")
public class ChuongController extends BaseController {
    private final ChuongService chuongService;
    public ChuongController(ChuongService chuongService) { this.chuongService = chuongService; }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(chuongService.findAll(), "Lấy danh sách chuồng thành công");
    }
}