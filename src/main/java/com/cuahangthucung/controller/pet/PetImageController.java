package com.cuahangthucung.controller.pet;

import com.cuahangthucung.controller.base.BaseController;
import com.cuahangthucung.entity.pet.entity.PetImage;
import com.cuahangthucung.service.pet.PetImageService;
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

    @PostMapping
    public ResponseEntity<Map<String, Object>> addImage(@RequestBody PetImage petImage) {
        return resCreated(petImageService.save(petImage), "Lưu hình ảnh thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteImage(@PathVariable Integer id) {
        petImageService.deleteById(id);
        return resSuccess(null, "Xóa hình ảnh thành công");
    }
}