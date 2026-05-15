package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.repository.user.HoSoNhanVienRepository;
import com.cuahangthucung.repository.user.HoSoNhanVienSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoSoNhanVienServiceImpl extends BaseServiceImpl<HoSoNhanVien, Integer, HoSoNhanVienRepository> implements HoSoNhanVienService {

    public HoSoNhanVienServiceImpl(HoSoNhanVienRepository repository) {
        super(repository);
    }

    @Override
    public List<HoSoNhanVienDTO> search(HoSoNhanVienSearchRequest request) {
        return repository.findAll(HoSoNhanVienSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HoSoNhanVienDTO saveRequest(HoSoNhanVienRequest request) {
        HoSoNhanVien hs = (request.getMaHoSo() != null)
                ? repository.findById(request.getMaHoSo()).orElse(new HoSoNhanVien())
                : new HoSoNhanVien();

        BeanUtils.copyProperties(request, hs);
        return convertToDTO(repository.save(hs));
    }

    @Override
    public HoSoNhanVienDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ mã: " + id));
    }

    @Override
    public HoSoNhanVienDTO findByMaNV(Integer maNV) {
        return repository.findByMaNV(maNV) != null 
                ? convertToDTO(repository.findByMaNV(maNV)) 
                : null;
    }

    private HoSoNhanVienDTO convertToDTO(HoSoNhanVien entity) {
        HoSoNhanVienDTO dto = new HoSoNhanVienDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            dto.setMaNV(entity.getNhanVien().getMaNV());
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }
        return dto;
    }
    @Override
    public List<HoSoNhanVienDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}