package com.cuahangthucung.controller.pet;


import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.service.pet.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.service.pet.PetService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor // Tự động tạo Constructor để tiêm PetService
//@CrossOrigin("*") // Cho phép Frontend gọi API
public class PetController extends BaseController {

    private final PetService petService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return resSuccess(petService.findAllDTO(), "Lấy danh sách thú cưng thành công");
    }
    /**
     * 1. Tìm kiếm, Lọc và Sắp xếp động
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(
            PetSearchRequest request,
            @PageableDefault(sort = "maPet", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Pet> resultPage = petService.findAll(
                com.cuahangthucung.repository.pet.PetSpecification.getFilter(request),
                pageable
        );
        return resSuccess(resultPage.map(petService::convertToDTO), "Tìm kiếm thú cưng thành công");
    }

    /**
     * 1. Tìm kiếm và lọc thú cưng
     * Ví dụ: GET /api/pets/search?tenPet=Lu&giong=Corgi
     *//*
    @GetMapping("/search")
    public ResponseEntity<List<PetDTO>> search(PetSearchRequest request) {
        List<PetDTO> result = petService.search(request);
        return ResponseEntity.ok(result);
    }
*/
    /**
     * 2. Lấy thông tin chi tiết một thú cưng
     */
    @GetMapping("/{maPet}")
    public ResponseEntity<?> getById(@PathVariable String maPet) {
        return resSuccess(petService.findByIdDTO(maPet), "Tìm thấy thú cưng: " + maPet);
    }

    /**
     * 3. Thêm mới thú cưng
     * PetRequest chứa maChuong, maKH, maNV dạng ID đơn giản
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PetRequest request) {
        return resCreated(petService.saveRequest(request), "Tiếp nhận thú cưng mới thành công");
    }
    /**
     * 4. Cập nhật   thông tin thú cưng
     */
    /*
    @PutMapping("/{maPet}")
    public ResponseEntity<PetDTO> update(
            @PathVariable String maPet,
            @Valid @RequestBody PetRequest request) {
        request.setMaPet(maPet);
        return ResponseEntity.ok(petService.saveRequest(request));
    }
*/
    @PutMapping("/{maPet}")
    public ResponseEntity<?> update(@PathVariable String maPet, @RequestBody PetRequest request) {
        request.setMaPet(maPet);
        return resSuccess(petService.saveRequest(request), "Cập nhật thông tin thú cưng thành công");
    }

    /**
     * 5. Xóa thú cưng
     */
    @DeleteMapping("/{maPet}")
    public ResponseEntity<?> delete(@PathVariable String maPet) {
        petService.deleteById(maPet);
        return resSuccess(null, "Đã xóa thú cưng khỏi hệ thống");
    }

    /**
     * 6. Lấy số liệu thống kê cho Dashboard
     * Trả về: tổng số lượng, tổng giá trị, số pet bệnh...
     */
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        return resSuccess(petService.getSummary(), "Lấy thống kê thú cưng thành công");
    }

    /**
     * 7. API bổ trợ để Frontend lấy mã Pet tự động trước khi thêm
     */
    @GetMapping("/next-id")
    public ResponseEntity<?> getNextMaPet() {
        return resSuccess(petService.generateNextMaPet(), "Lấy mã ID tự động thành công");
    }



}