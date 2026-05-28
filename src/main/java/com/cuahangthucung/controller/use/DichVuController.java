package com.cuahangthucung.controller.use;

import com.cuahangthucung.dto.use.dichvu.DichVuDTO;
import com.cuahangthucung.dto.use.dichvu.DichVuRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSearchRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSummaryDTO;
import com.cuahangthucung.service.use.service.DichVuService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dich-vu")
//@CrossOrigin(origins = "*") // Hỗ trợ kết nối ứng dụng với Frontend (CORS)
public class DichVuController {

    private final DichVuService dichVuService;

    public DichVuController(DichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    /**
     * 1. Tìm kiếm dịch vụ nâng cao (theo tên, khoảng giá, hoặc keyword tổng hợp) kết hợp phân trang
     * GET /api/v1/dich-vu/search
     */
    @GetMapping("/search")
    public ResponseEntity<Page<DichVuDTO>> search(
            DichVuSearchRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "maDV") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DichVuDTO> result = dichVuService.search(request, pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * 2. Lấy danh sách toàn bộ các dịch vụ (Dạng phẳng không phân trang)
     * GET /api/v1/dich-vu
     */
    @GetMapping
    public ResponseEntity<List<DichVuDTO>> getAll() {
        List<DichVuDTO> list = dichVuService.findAllDTO();
        return ResponseEntity.ok(list);
    }

    /**
     * 3. Lấy thông tin chi tiết một dịch vụ cụ thể theo ID
     * GET /api/v1/dich-vu/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DichVuDTO> getById(@PathVariable("id") String id) {
        DichVuDTO dto = dichVuService.findByIdDTO(id.trim());
        return ResponseEntity.ok(dto);
    }

    /**
     * 4. Lưu mới hoặc cập nhật một dịch vụ dựa trên dữ liệu Request gửi lên
     * POST /api/v1/dich-vu
     */
    @PostMapping
    public ResponseEntity<DichVuDTO> saveOrUpdate(@Valid @RequestBody DichVuRequest request) {
        DichVuDTO result = dichVuService.saveRequest(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 5. Lấy bảng tổng hợp số liệu dữ liệu thống kê kho dịch vụ (Đếm tổng, giá min, max, avg)
     * GET /api/v1/dich-vu/summary
     */
    @GetMapping("/summary")
    public ResponseEntity<DichVuSummaryDTO> getSummary() {
        DichVuSummaryDTO summary = dichVuService.getSummary();
        return ResponseEntity.ok(summary);
    }

    /**
     * 6. Lấy chuỗi mã định danh dịch vụ (Mã ID) kế tiếp tự động sinh phục vụ màn hình Thêm mới
     * GET /api/v1/dich-vu/next-code
     */
    @GetMapping("/next-code")
    public ResponseEntity<String> getNextMaDV() {
        String nextCode = dichVuService.generateNextMaDV();
        return ResponseEntity.ok(nextCode);
    }

    /**
     * 7. Kiểm tra trùng lặp tên dịch vụ (Hỗ trợ Frontend hiển thị cảnh báo Validate trực tiếp khi người dùng nhập)
     * GET /api/v1/dich-vu/exists-name
     */
    @GetMapping("/exists-name")
    public ResponseEntity<Boolean> checkTenDVExists(@RequestParam("tenDV") String tenDV) {
        boolean exists = dichVuService.isTenDVExists(tenDV.trim());
        return ResponseEntity.ok(exists);
    }

    /**
     * 8. Tìm kiếm nhanh các dịch vụ có mức giá nằm trong khoảng chỉ định
     * GET /api/v1/dich-vu/gia-range?min=...&max=...
     */
    @GetMapping("/gia-range")
    public ResponseEntity<List<DichVuDTO>> getByGiaRange(
            @RequestParam(value = "min", defaultValue = "0") double min,
            @RequestParam(value = "max", defaultValue = "999999999") double max) {
        List<DichVuDTO> list = dichVuService.findByGiaRange(min, max);
        return ResponseEntity.ok(list);
    }

    /**
     * 9. Xóa một dịch vụ khỏi hệ thống quản lý dữ liệu cửa hàng thú cưng
     * DELETE /api/v1/dich-vu/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        dichVuService.deleteById(id.trim());
        return ResponseEntity.ok("Đã loại bỏ hoàn toàn dịch vụ có mã định danh: " + id);
    }
}