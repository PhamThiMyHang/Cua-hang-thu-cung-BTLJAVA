package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import com.cuahangthucung.repository.pet.LoaiChuongRepository;
import com.cuahangthucung.repository.pet.LoaiChuongSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoaiChuongServiceImpl extends BaseServiceImpl<LoaiChuong, String, LoaiChuongRepository>
        implements LoaiChuongService {

    public LoaiChuongServiceImpl(LoaiChuongRepository repository) {
        super(repository);
    }

    @Override
    public List<LoaiChuongDTO> search(LoaiChuongSearchRequest request) {
        return repository.findAll(LoaiChuongSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoaiChuongDTO saveRequest(LoaiChuongRequest request) {
        LoaiChuong entity = new LoaiChuong();
        if (request.getMaLoaiChuong() != null) {
            entity = repository.findById(request.getMaLoaiChuong()).orElse(new LoaiChuong());
        }
        BeanUtils.copyProperties(request, entity);
        LoaiChuong saved = repository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    public LoaiChuongSummaryDTO getSummary() {
        Long tongSoLoai = repository.count();
        BigDecimal giaTrungBinh = repository.getAverageGiaThue();
        Long tongSucChua = repository.getTotalSystemCapacity();
        String phoBienNhat = repository.getNameOfMostPopularType().orElse("N/A");

        return new LoaiChuongSummaryDTO(
                tongSoLoai,
                giaTrungBinh != null ? giaTrungBinh : BigDecimal.ZERO,
                phoBienNhat,
                tongSucChua != null ? tongSucChua : 0L
        );
    }

    @Override
    public List<LoaiChuongDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LoaiChuongDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy loại chuồng: " + id));
    }

    private LoaiChuongDTO convertToDTO(LoaiChuong entity) {
        LoaiChuongDTO dto = new LoaiChuongDTO();
        BeanUtils.copyProperties(entity, dto);

        // Tính toán số chuồng thực tế và số chuồng trống
        if (entity.getDanhSachChuong() != null) {
            dto.setSoChuongConTrong((long) entity.getDanhSachChuong().size());

            long countTrong = entity.getDanhSachChuong().stream()
                    .filter(chuong -> chuong.getTrangThai() == TrangThaiChuong.TRONG)
                    .count();
            // Đảm bảo trong LoaiChuongDTO bạn có trường này (kiểu Long hoặc Integer)
            dto.setSoChuongConTrong(countTrong);
        } else {
            dto.setSoChuongConTrong(0L);
            dto.setSoChuongConTrong(0L);
        }

        return dto;
    }

}