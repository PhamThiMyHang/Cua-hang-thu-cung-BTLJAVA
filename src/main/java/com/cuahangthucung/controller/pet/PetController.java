package com.cuahangthucung.controller.pet;


import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.service.pet.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@CrossOrigin("*") // Cho phép Frontend gọi API
public class PetController {

    private final PetService petService;

    /**
     * 1. Tìm kiếm và lọc thú cưng
     * Ví dụ: GET /api/pets/search?tenPet=Lu&giong=Corgi
     */
    @GetMapping("/search")
    public ResponseEntity<List<PetDTO>> search(PetSearchRequest request) {
        List<PetDTO> result = petService.search(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 2. Lấy thông tin chi tiết một thú cưng
     */
    @GetMapping("/{maPet}")
    public ResponseEntity<PetDTO> getById(@PathVariable String maPet) {
        return ResponseEntity.ok(petService.findByIdDTO(maPet));
    }

    /**
     * 3. Thêm mới thú cưng
     * PetRequest chứa maChuong, maKH, maNV dạng ID đơn giản
     */
    @PostMapping
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetRequest request) {
        PetDTO savedPet = petService.saveRequest(request);
        return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
    }

    /**
     * 4. Cập nhật   thông tin thú cưng
     */
    @PutMapping("/{maPet}")
    public ResponseEntity<PetDTO> update(
            @PathVariable String maPet,
            @Valid @RequestBody PetRequest request) {
        request.setMaPet(maPet);
        return ResponseEntity.ok(petService.saveRequest(request));
    }

    /**
     * 5. Xóa thú cưng
     */
    @DeleteMapping("/{maPet}")
    public ResponseEntity<Void> delete(@PathVariable String maPet) {
        petService.deleteById(maPet);
        return ResponseEntity.noContent().build();
    }

    /**
     * 6. Lấy số liệu thống kê cho Dashboard
     * Trả về: tổng số lượng, tổng giá trị, số pet bệnh...
     */
    @GetMapping("/summary")
    public ResponseEntity<PetSummaryDTO> getSummary() {
        return ResponseEntity.ok(petService.getSummary());
    }

    /**
     * 7. API bổ trợ để Frontend lấy mã Pet tự động trước khi thêm
     */
    @GetMapping("/next-id")
    public ResponseEntity<String> getNextMaPet() {
        return ResponseEntity.ok(petService.generateNextMaPet());
    }
        /*
=======
import java.util.Map;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin("*") // Cho phép Frontend gọi API từ domain khác
public class PetController extends BaseController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Pet> list = petService.findAll();
        return resSuccess(list, "Lấy danh sách thú cưng thành công");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        return petService.findById(id)
                .map(pet -> resSuccess(pet, "Tìm thấy thú cưng"))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Pet pet) {
        Pet savedPet = petService.save(pet);
        return resCreated(savedPet, "Thêm mới thú cưng thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable String id, @RequestBody Pet pet) {
        Pet updatedPet = petService.update(id, pet);
        return resSuccess(updatedPet, "Cập nhật thông tin thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        petService.deleteById(id);
        return resSuccess(null, "Xóa thú cưng thành công");
>>>>>>> DoanThiNgocGiau
    */
}