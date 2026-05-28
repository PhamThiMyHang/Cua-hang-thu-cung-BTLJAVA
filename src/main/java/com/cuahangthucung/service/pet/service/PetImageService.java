package com.cuahangthucung.service.pet.service;

import com.cuahangthucung.dto.pet.petImage.PetImageDTO;
import com.cuahangthucung.dto.pet.petImage.PetImageRequest;
import com.cuahangthucung.dto.pet.petImage.PetImageSearchRequest;
import com.cuahangthucung.dto.pet.petImage.PetImageSummaryDTO;
import com.cuahangthucung.entity.pet.entity.PetImage;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface PetImageService extends BaseService<PetImage, Integer> {
    // 1, 2, 3, 5, 6. Tìm kiếm danh sách ảnh theo Request (Mã ảnh, Mã Pet, Ngày cụ thể)
    List<PetImageDTO> search(PetImageSearchRequest request);

    // Lưu ảnh từ Request (Add/Update)
    PetImageDTO saveRequest(PetImageRequest request);

    List<PetImageDTO> findAllDTO();

    PetImageDTO findByIdDTO(Integer id);

    // 2, 3, 5. Lấy các số liệu count và tổng hợp
    PetImageSummaryDTO getSummary(PetImageSearchRequest request);

    PetImageDTO convertToDTO(PetImage entity);

}

