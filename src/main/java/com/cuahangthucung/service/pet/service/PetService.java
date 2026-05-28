package com.cuahangthucung.service.pet.service;

import com.cuahangthucung.dto.pet.pet.PetDTO;
import com.cuahangthucung.dto.pet.pet.PetRequest;
import com.cuahangthucung.dto.pet.pet.PetSearchRequest;
import com.cuahangthucung.dto.pet.pet.PetSummaryDTO;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface PetService extends BaseService<Pet, String> {
    // Sinh mã tự động
    String generateNextMaPet();

    // Tìm kiếm động trả về DTO
    List<PetDTO> search(PetSearchRequest request);

    // Lưu mới/Cập nhật từ PetRequest
    PetDTO saveRequest(PetRequest request);

    // Lấy chi tiết DTO
    PetDTO findByIdDTO(String id);

    // Lấy thống kê tổng hợp
    PetSummaryDTO getSummary();

    boolean hasSeriousHealthIssue(String maPet);
/*them chuc nang */
    List<PetDTO> findAllDTO();

    PetDTO convertToDTO(Pet pet);

}