package com.cuahangthucung.controller.use;


import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.use.donhang.DonHangDTO;
import com.cuahangthucung.dto.use.donhang.DonHangRequest;
import com.cuahangthucung.dto.use.donhang.DonHangSearchRequest;
import com.cuahangthucung.service.use.service.DonHangService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * =============================================
 * DONHANG CONTROLLER
 * =============================================
 * Quản lý đơn hàng bán hàng
 */
@RestController
@RequestMapping("/api/don-hang")
//@CrossOrigin("*")
public class DonHangController extends BaseController {

    private final DonHangService donHangService;

    public DonHangController(DonHangService donHangService) {
        this.donHangService = donHangService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            DonHangSearchRequest request,
            @PageableDefault(size = 10, sort = "ngayTao", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // Hoạt động hoàn hảo: Nhận Pageable truyền xuống Service và trả về Page<DonHangDTO>
        Page<DonHangDTO> resultPage = donHangService.search(request, pageable);
        return resSuccess(resultPage, "Tìm kiếm đơn hàng thành công");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        return resSuccess(donHangService.findAllDTO(), "Lấy danh sách đơn hàng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return resSuccess(donHangService.findByIdDTO(id), "Lấy thông tin đơn hàng thành công");
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody DonHangRequest request) {
        DonHangDTO saved = donHangService.saveRequest(request);
        return resCreated(saved, "Tạo đơn hàng mới thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @Valid @RequestBody DonHangRequest request) {
        request.setMaDH(id);
        DonHangDTO updated = donHangService.saveRequest(request);
        return resSuccess(updated, "Cập nhật đơn hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        donHangService.deleteById(id);
        return resSuccess(null, "Xóa đơn hàng thành công");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        return resSuccess(donHangService.getSummary(), "Lấy thống kê đơn hàng thành công");
    }
}