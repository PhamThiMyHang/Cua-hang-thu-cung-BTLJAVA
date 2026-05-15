package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
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