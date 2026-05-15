package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.dto.pet.LichSuSearchRequest;
import com.cuahangthucung.dto.pet.LichSuSucKhoeDTO;
import com.cuahangthucung.dto.pet.LichSuSucKhoeRequest;
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.service.pet.LichSuSucKhoeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    /**
     * 1-8. Tìm kiếm, Lọc và PHÂN TRANG lịch sử sức khỏe
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            LichSuSearchRequest request,
            @PageableDefault(sort = "ngay", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var spec = com.cuahangthucung.repository.pet.LichSuSpecification.getFilter(request);
        Page<LichSuSucKhoe> resultPage = service.findAll(spec, pageable);

        // Bọc vào resSuccess để có message và status đồng nhất
        return resSuccess(resultPage.map(service::convertToDTO), "Tìm kiếm lịch sử thành công");
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
    @GetMapping("/count")
    public ResponseEntity<?> count(LichSuSearchRequest request) {
        long total = service.countByRequest(request);
        return resSuccess(total, "Thống kê số lượng thành công");
    }
}