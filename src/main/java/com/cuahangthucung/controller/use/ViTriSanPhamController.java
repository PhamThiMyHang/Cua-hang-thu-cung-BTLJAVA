package com.cuahangthucung.controller.use;

import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamDTO;
import com.cuahangthucung.dto.use.ViTriSanPham.ViTriSanPhamRequest;
import com.cuahangthucung.service.use.service.ViTriSanPhamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vitri")
@RequiredArgsConstructor
public class ViTriSanPhamController {

    private final ViTriSanPhamService service;

    @GetMapping
    public List<ViTriSanPhamDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ViTriSanPhamDTO findById(
            @PathVariable String id) {

        return service.findById(id);
    }

    @PostMapping
    public ViTriSanPhamDTO create(
            @RequestBody
            @Valid
            ViTriSanPhamRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public ViTriSanPhamDTO update(
            @PathVariable String id,
            @RequestBody
            @Valid
            ViTriSanPhamRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id) {

        service.delete(id);
    }
}