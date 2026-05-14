package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.PetImage;
import com.cuahangthucung.repository.pet.PetImageRepository;
import com.cuahangthucung.repository.pet.PetImageSpecification;
import com.cuahangthucung.repository.pet.PetRepository; // Cần tiêm thêm để tìm Pet
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetImageServiceImpl extends BaseServiceImpl<PetImage, Integer, PetImageRepository>
        implements PetImageService {

    private final PetRepository petRepository;

    public PetImageServiceImpl(PetImageRepository repository, PetRepository petRepository) {
        super(repository);
        this.petRepository = petRepository;
    }

    @Override
    public List<PetImageDTO> search(PetImageSearchRequest request) {
        return repository.findAll(PetImageSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PetImageDTO saveRequest(PetImageRequest request) {
        PetImage entity = new PetImage();
        if (request.getMaImg() != null) {
            entity = repository.findById(request.getMaImg()).orElse(new PetImage());
        }

        BeanUtils.copyProperties(request, entity);

        // Gán Pet từ mã pet gửi lên
        if (request.getMaPet() != null) {
            var pet = petRepository.findById(request.getMaPet())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thú cưng"));
            entity.setPet(pet);
        }

        return convertToDTO(repository.save(entity));
    }

    @Override
    public PetImageSummaryDTO getSummary(PetImageSearchRequest request) {
        PetImageSummaryDTO summary = new PetImageSummaryDTO();

        // Tổng số ảnh toàn hệ thống
        summary.setTongSoLuongAnh(repository.count());

        // Yêu cầu 2: Count theo MaPet
        if (request.getMaPet() != null) {
            summary.setSoAnhCuaPet(repository.countByPet_MaPet(request.getMaPet()));
        }

        // Yêu cầu 3 & 5: Count theo Ngày và Pet+Ngày
        if (request.getNgayCuThe() != null) {
            var start = request.getNgayCuThe().atStartOfDay();
            var end = request.getNgayCuThe().atTime(LocalTime.MAX);

            summary.setSoAnhTrongNgay(repository.countByDateRange(start, end));

            if (request.getMaPet() != null) {
                summary.setSoAnhPetTheoNgay(repository.countByPetAndDateRange(request.getMaPet(), start, end));
            }
        }

        return summary;
    }

    @Override
    public List<PetImageDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PetImageDTO findByIdDTO(Integer id) {
        return repository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ảnh mã: " + id));
    }

    private PetImageDTO convertToDTO(PetImage entity) {
        PetImageDTO dto = new PetImageDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getPet() != null) {
            dto.setMaPet(entity.getPet().getMaPet());
        }
        return dto;
    }
}