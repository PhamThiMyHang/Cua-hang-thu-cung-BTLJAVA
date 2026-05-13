package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.service.pet.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    }
}