package com.cuahangthucung.controller.use;


import com.cuahangthucung.dto.use.giohang.GioHangDTO;
import com.cuahangthucung.dto.use.giohang.GioHangRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSearchRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSummaryDTO;
import com.cuahangthucung.service.use.service.GioHangService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gio-hang")
public class GioHangController {

    private final GioHangService gioHangService;

    public GioHangController(GioHangService gioHangService) {
        this.gioHangService = gioHangService;
    }

    /**
     * 1. Tìm kiếm giỏ hàng (có thể kết hợp phân trang sau)
     */
    @GetMapping("/search")
    public ResponseEntity<List<GioHangDTO>> search(GioHangSearchRequest request) {
        List<GioHangDTO> list = gioHangService.search(request);
        return ResponseEntity.ok(list);
    }

    /**
     * 2. Thêm mới hoặc cập nhật sản phẩm vào giỏ hàng
     */
    @PostMapping
    public ResponseEntity<GioHangDTO> save(@Valid @RequestBody GioHangRequest request) {
        GioHangDTO result = gioHangService.saveRequest(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 3. Lấy chi tiết một item trong giỏ hàng
     */
    @GetMapping("/detail")
    public ResponseEntity<GioHangDTO> getById(
            @RequestParam("maGioHang") String maGioHang,
            @RequestParam("maSP") String maSP) {
        GioHangDTO dto = gioHangService.findByIdDTO(maGioHang, maSP);
        return ResponseEntity.ok(dto);
    }

    /**
     * 4. Lấy toàn bộ giỏ hàng của một User
     */
    @GetMapping("/user/{maUser}")
    public ResponseEntity<List<GioHangDTO>> getByMaUser(@PathVariable String maUser) {
        List<GioHangDTO> list = gioHangService.findByMaUser(maUser);
        return ResponseEntity.ok(list);
    }

    /**
     * 5. Tính tổng tiền của một giỏ hàng
     */
    @GetMapping("/{maGioHang}/tong-tien")
    public ResponseEntity<BigDecimal> getTongTien(@PathVariable String maGioHang) {
        BigDecimal total = gioHangService.tinhTongTien(maGioHang);
        return ResponseEntity.ok(total);
    }

    /**
     * 6. Xóa một sản phẩm khỏi giỏ hàng
     */
    @DeleteMapping("/detail")
    public ResponseEntity<String> deleteItem(
            @RequestParam("maGioHang") String maGioHang,
            @RequestParam("maSP") String maSP) {
        gioHangService.deleteByMaGioHangAndMaSP(maGioHang, maSP);
        return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ hàng thành công");
    }

    /**
     * 7. Thống kê tổng quan giỏ hàng (Dashboard)
     */
    @GetMapping("/summary")
    public ResponseEntity<GioHangSummaryDTO> getSummary() {
        GioHangSummaryDTO summary = gioHangService.getSummary();
        return ResponseEntity.ok(summary);
    }
}