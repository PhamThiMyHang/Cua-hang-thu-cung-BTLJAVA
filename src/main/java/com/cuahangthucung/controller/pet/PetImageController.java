
package com.cuahangthucung.controller.pet;


import com.cuahangthucung.controller.base.BaseController;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.service.pet.PetImageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.cuahangthucung.entity.pet.entity.PetImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/pet-images")
@CrossOrigin("*")
public class PetImageController extends BaseController {

    private final PetImageService petImageService;

    public PetImageController(PetImageService petImageService) {
        this.petImageService = petImageService;
    }

    /**
     * 1, 2, 3, 5, 6. Tìm kiếm và lọc ảnh nâng cao
     * Hỗ trợ lọc theo: maImg, maPet, ngayCuThe
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(PetImageSearchRequest request) {
        List<PetImageDTO> list = petImageService.search(request);
        return resSuccess(list, "Tìm kiếm danh sách hình ảnh thành công");
    }

    /**
     * Lấy toàn bộ danh sách ảnh (DTO)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<PetImageDTO> list = petImageService.findAllDTO();
        return resSuccess(list, "Lấy toàn bộ danh sách hình ảnh thành công");
    }

    /**
     * Lấy chi tiết một ảnh theo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Integer id) {
        PetImageDTO dto = petImageService.findByIdDTO(id);
        return resSuccess(dto, "Tìm thấy thông tin hình ảnh");
    }

    /**
     * Thêm mới hình ảnh (Sử dụng PetImageRequest thay vì Entity)
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody PetImageRequest request) {
        PetImageDTO savedDto = petImageService.saveRequest(request);
        return resCreated(savedDto, "Lưu hình ảnh thành công");
    }

    /**
     * Cập nhật thông tin hình ảnh
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Integer id,
            @Valid @RequestBody PetImageRequest request) {
        request.setMaImg(id);
        PetImageDTO updatedDto = petImageService.saveRequest(request);
        return resSuccess(updatedDto, "Cập nhật hình ảnh thành công");
    }

    /**
     * Xóa hình ảnh
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Integer id) {
        petImageService.deleteById(id);
        return resSuccess(null, "Xóa hình ảnh thành công");
    }

    /**
     * Thống kê số lượng ảnh (Count)
     * Đáp ứng các yêu cầu: count theo pet, count theo ngày, count pet+ngày
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary(PetImageSearchRequest request) {
        PetImageSummaryDTO summary = petImageService.getSummary(request);
        return resSuccess(summary, "Lấy thống kê hình ảnh thành công");
    }

}