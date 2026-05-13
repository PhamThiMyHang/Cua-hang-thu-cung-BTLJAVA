package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.service.pet.LichSuSucKhoeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pet-histories")
@CrossOrigin("*")
public class LichSuSucKhoeController extends BaseController {

    private final LichSuSucKhoeService lichSuSucKhoeService;

    public LichSuSucKhoeController(LichSuSucKhoeService lichSuSucKhoeService) {
        this.lichSuSucKhoeService = lichSuSucKhoeService;
    }

    @GetMapping("/pet/{maPet}")
    public ResponseEntity<Map<String, Object>> getByPetId(@PathVariable String maPet) {
        // Lưu ý: Sau này bạn có thể viết thêm hàm tìm kiếm theo MaPet trong Service
        // Hiện tại ta dùng hàm findAll đơn giản
        return resSuccess(lichSuSucKhoeService.findAll(), "Lấy lịch sử sức khỏe thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody LichSuSucKhoe lichSu) {
        return resCreated(lichSuSucKhoeService.save(lichSu), "Thêm bệnh án thành công");
    }
}