package com.cuahangthucung.controller.use;

import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangDTO;
import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangRequest;
import com.cuahangthucung.service.use.service.ChiTietDonHangService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chi-tiet-don-hang")
//@CrossOrigin(origins = "*") // Hỗ trợ kết nối Frontend nếu cần
public class ChiTietDonHangController {

    private final ChiTietDonHangService chiTietDonHangService;

    public ChiTietDonHangController(ChiTietDonHangService chiTietDonHangService) {
        this.chiTietDonHangService = chiTietDonHangService;
    }

    /**
     * 1. Thêm mới hoặc cập nhật một dòng chi tiết đơn hàng
     * POST /api/v1/chi-tiet-don-hang
     */
    @PostMapping
    public ResponseEntity<ChiTietDonHangDTO> saveOrCreate(@Valid @RequestBody ChiTietDonHangRequest request) {
        ChiTietDonHangDTO result = chiTietDonHangService.saveRequest(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 2. Lấy thông tin chi tiết đơn hàng cụ thể theo Khóa chính kép (Mã đơn hàng + Mã sản phẩm)
     * GET /api/v1/chi-tiet-don-hang/detail?maDH=...&maSP=...
     */
    @GetMapping("/detail")
    public ResponseEntity<ChiTietDonHangDTO> getById(
            @RequestParam("maDH") String maDH,
            @RequestParam("maSP") String maSP) {
        ChiTietDonHangDTO dto = chiTietDonHangService.findByIdDTO(maDH.trim(), maSP.trim());
        return ResponseEntity.ok(dto);
    }

    /**
     * 3. Lấy danh sách toàn bộ các sản phẩm nằm trong một đơn hàng
     * GET /api/v1/chi-tiet-don-hang/don-hang/{maDH}
     */
    @GetMapping("/don-hang/{maDH}")
    public ResponseEntity<List<ChiTietDonHangDTO>> getByMaDH(@PathVariable("maDH") String maDH) {
        List<ChiTietDonHangDTO> list = chiTietDonHangService.findByMaDH(maDH.trim());
        return ResponseEntity.ok(list);
    }

    /**
     * 4. Lấy lịch sử biến động/xuất hiện của một sản phẩm trong các đơn hàng
     * GET /api/v1/chi-tiet-don-hang/san-pham/{maSP}
     */
    @GetMapping("/san-pham/{maSP}")
    public ResponseEntity<List<ChiTietDonHangDTO>> getByMaSP(@PathVariable("maSP") String maSP) {
        List<ChiTietDonHangDTO> list = chiTietDonHangService.findByMaSP(maSP.trim());
        return ResponseEntity.ok(list);
    }

    /**
     * 5. Tính tổng tiền thực tế của một đơn hàng dựa trên tổng (soLuong * donGia) từ các chi tiết
     * GET /api/v1/chi-tiet-don-hang/don-hang/{maDH}/tong-tien
     */
    @GetMapping("/don-hang/{maDH}/tong-tien")
    public ResponseEntity<BigDecimal> getTongTienDonHang(@PathVariable("maDH") String maDH) {
        BigDecimal tongTien = chiTietDonHangService.tinhTongTien(maDH.trim());
        return ResponseEntity.ok(tongTien);
    }

    /**
     * 6. Xóa một sản phẩm cụ thể ra khỏi đơn hàng (Dựa trên khóa kép)
     * DELETE /api/v1/chi-tiet-don-hang/detail?maDH=...&maSP=...
     */
    @DeleteMapping("/detail")
    public ResponseEntity<String> deleteSingleItem(
            @RequestParam("maDH") String maDH,
            @RequestParam("maSP") String maSP) {
        // Chuyển đổi ID để gọi hàm xóa của BaseService
        com.cuahangthucung.entity.use.entity.ChiTietDonHangId id = new com.cuahangthucung.entity.use.entity.ChiTietDonHangId();
        id.setDonHang(maDH.trim());
        id.setSanPham(maSP.trim());

        chiTietDonHangService.deleteById(id);
        return ResponseEntity.ok("Đã xóa sản phẩm " + maSP + " ra khỏi đơn hàng " + maDH + " thành công.");
    }

    /**
     * 7. Hủy/Xóa toàn bộ các chi tiết thuộc về một đơn hàng
     * DELETE /api/v1/chi-tiet-don-hang/don-hang/{maDH}
     */
    @DeleteMapping("/don-hang/{maDH}")
    public ResponseEntity<String> clearAllByMaDH(@PathVariable("maDH") String maDH) {
        chiTietDonHangService.deleteByMaDH(maDH.trim());
        return ResponseEntity.ok("Đã giải phóng toàn bộ chi tiết vật phẩm của đơn hàng " + maDH);
    }
}