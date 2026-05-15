package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import com.cuahangthucung.exception.ResourceNotFoundException;

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
        LoaiChuong loaiChuong;

        // 1. Kiểm tra thêm mới hay cập nhật
        if (request.getMaLoaiChuong() != null && !request.getMaLoaiChuong().trim().isEmpty()) {
            loaiChuong = repository.findById(request.getMaLoaiChuong())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại chuồng mã: " + request.getMaLoaiChuong()));
        } else {
            loaiChuong = new LoaiChuong();
            // Giả sử bạn viết hàm này tương tự như bên Pet
            String newId = generateNextMaLoaiChuong();
            loaiChuong.setMaLoaiChuong(newId);
        }

        // 2. Copy dữ liệu cơ bản (TenLoai, GiaThue, MoTa, SoLuong thiết kế)
        // Loại trừ maLoaiChuong để không bị đè dữ liệu null/sai vào PK
        BeanUtils.copyProperties(request, loaiChuong, "maLoaiChuong", "danhSachChuong");

        // 3. Lưu vào Database
        LoaiChuong saved = repository.save(loaiChuong);

        // 4. Trả về DTO (Hàm convertToDTO sẽ tính toán soChuongConTrong)
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
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy loại chuồng: " + id));
    }

    @Override
    public LoaiChuongDTO convertToDTO(LoaiChuong entity) {
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

    public String generateNextMaLoaiChuong() {
        String maxId = repository.findMaxMaLoaiChuong(); // Kết quả ví dụ: "LC005"

        if (maxId == null || maxId.trim().isEmpty()) {
            return "LC001"; // Nếu bảng chưa có dữ liệu
        }

        try {
            // Tách bỏ chữ "LC", lấy phần số "005" -> chuyển thành 5
            int nextNumber = Integer.parseInt(maxId.substring(2)) + 1;

            // Format lại thành LC + 3 chữ số (ví dụ: LC006)
            return String.format("LC%03d", nextNumber);
        } catch (Exception e) {
            return "LC001"; // Phòng trường hợp mã cũ bị sai định dạng
        }
    }


}