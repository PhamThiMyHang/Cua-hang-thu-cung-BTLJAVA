package com.cuahangthucung.controller.use;

import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapDTO;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapRequest;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapSearchRequest;
import com.cuahangthucung.service.use.service.NhaCungCapService;
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
@RequestMapping("/api/v1/nha-cung-cap")
//@CrossOrigin(origins = "*") // Hỗ trợ kết nối liên thông với ứng dụng Frontend (CORS)
public class NhaCungCapController {

    private final NhaCungCapService nhaCungCapService;

    public NhaCungCapController(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
    }

    /**
     * 1. Tìm kiếm nâng cao qua bộ lọc Specification tích hợp phân trang và sắp xếp dữ liệu
     * GET /api/v1/nha-cung-cap/search
     */
    @GetMapping("/search")
    public ResponseEntity<Page<NhaCungCapDTO>> search(
            NhaCungCapSearchRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "maNCC") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Sort sort = direction.equalsIgnoreCase(Sort.Direction.DESC.name())
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NhaCungCapDTO> result = nhaCungCapService.search(request, pageable);
        return ResponseEntity.ok(result);
    }

    /**
     * 2. Lấy danh sách phẳng toàn bộ các Nhà cung cấp (Không phân trang)
     * GET /api/v1/nha-cung-cap
     */
    @GetMapping
    public ResponseEntity<List<NhaCungCapDTO>> getAll() {
        List<NhaCungCapDTO> list = nhaCungCapService.findAllDTO();
        return ResponseEntity.ok(list);
    }

    /**
     * 3. Tìm kiếm chi tiết thông tin một Nhà cung cấp cụ thể dựa theo mã ID
     * GET /api/v1/nha-cung-cap/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NhaCungCapDTO> getById(@PathVariable("id") String id) {
        NhaCungCapDTO dto = nhaCungCapService.findByIdDTO(id.trim());
        return ResponseEntity.ok(dto);
    }

    /**
     * 4. Tiếp nhận yêu cầu Thêm mới hoàn toàn hoặc Cập nhật thông tin một Nhà cung cấp
     * POST /api/v1/nha-cung-cap
     */
    @PostMapping
    public ResponseEntity<NhaCungCapDTO> saveOrUpdate(@Valid @RequestBody NhaCungCapRequest request) {
        NhaCungCapDTO result = nhaCungCapService.saveRequest(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * 5. Gỡ bỏ thông tin một Nhà cung cấp ra khỏi hệ thống cơ sở dữ liệu của cửa hàng
     * DELETE /api/v1/nha-cung-cap/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id) {
        nhaCungCapService.deleteById(id.trim());
        return ResponseEntity.ok("Đã xóa bỏ hoàn toàn dữ liệu của nhà cung cấp có mã định danh: " + id);
    }
}