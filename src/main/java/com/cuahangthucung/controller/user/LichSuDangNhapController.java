package com.cuahangthucung.controller.user;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.service.user.LichSuDangNhapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/lich-su-dang-nhap")
@CrossOrigin("*")
public class LichSuDangNhapController extends BaseController {

    private final LichSuDangNhapService lichSuService;

    public LichSuDangNhapController(LichSuDangNhapService lichSuService) {
        this.lichSuService = lichSuService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getByUser(@PathVariable Integer userId) {
        return resSuccess(lichSuService.findByUserUserID(userId), "Lấy lịch sử đăng nhập thành công");
    }

    @GetMapping("/user/{userId}/top10")
    public ResponseEntity<Map<String, Object>> getTop10(@PathVariable Integer userId) {
        return resSuccess(lichSuService.findTop10ByUserUserIDOrderByThoiGianDesc(userId), "Lấy 10 lần đăng nhập gần nhất");
    }
}