package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.repository.user.LichTrucRepository;
import com.cuahangthucung.repository.user.LichTrucSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichTrucServiceImpl extends BaseServiceImpl<LichTruc, Integer, LichTrucRepository> implements LichTrucService {

    public LichTrucServiceImpl(LichTrucRepository repository) {
        super(repository);
    }

    @Override
    public List<LichTrucDTO> search(LichTrucSearchRequest request) {
        return repository.findAll(LichTrucSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LichTrucDTO saveRequest(LichTrucRequest request) {
        // Kiểm tra trùng lịch trực
        if (repository.existsByNhanVien_MaNVAndNgayAndCaLamViec(
                request.getMaNV(), request.getNgay(), request.getCaLamViec())) {
            throw new RuntimeException("Nhân viên đã có lịch trực ca này trong ngày!");
        }

        LichTruc lichTruc = (request.getId() != null)
                ? repository.findById(request.getId()).orElse(new LichTruc())
                : new LichTruc();

        BeanUtils.copyProperties(request, lichTruc);
        return convertToDTO(repository.save(lichTruc));
    }

    @Override
    public LichTrucDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch trực mã: " + id));
    }

    @Override
    public List<LichTrucDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LichTrucDTO convertToDTO(LichTruc entity) {
        LichTrucDTO dto = new LichTrucDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            dto.setMaNV(entity.getNhanVien().getMaNV());
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }
        return dto;
    }
}