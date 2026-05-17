package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.HoSoNhanVienRepository;
import com.cuahangthucung.repository.user.HoSoNhanVienSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoSoNhanVienServiceImpl extends BaseServiceImpl<HoSoNhanVien, Integer, HoSoNhanVienRepository> 
        implements HoSoNhanVienService {

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
    public Page<HoSoNhanVienDTO> search(HoSoNhanVienSearchRequest request, Pageable pageable) {
        return repository.findAll(HoSoNhanVienSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public HoSoNhanVienDTO saveRequest(HoSoNhanVienRequest request) {
        HoSoNhanVien hs = (request.getMaHoSo() != null)
                ? repository.findById(request.getMaHoSo()).orElse(new HoSoNhanVien())
                : new HoSoNhanVien();

        BeanUtils.copyProperties(request, hs, "nhanVien"); // ignore quan hệ
        // ĐÃ SỬA: Gán Proxy NhanVien tránh lỗi rỗng trường ràng buộc ngoại của DB
        if (request.getMaNV() != null) {
            NhanVien nvProxy = new NhanVien();
            nvProxy.setMaNV(request.getMaNV());
            hs.setNhanVien(nvProxy);
        }
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
        return Optional.ofNullable(repository.findByMaNV(maNV))
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ của nhân viên mã: " + maNV));
    }

    @Override
    public List<HoSoNhanVienDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
}