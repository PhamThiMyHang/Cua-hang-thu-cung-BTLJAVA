package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.repository.user.LichSuDangNhapRepository;
import com.cuahangthucung.repository.user.LichSuDangNhapSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichSuDangNhapServiceImpl extends BaseServiceImpl<LichSuDangNhap, Integer, LichSuDangNhapRepository> 
        implements LichSuDangNhapService {

    public LichSuDangNhapServiceImpl(LichSuDangNhapRepository repository) {
        super(repository);
    }

    @Override
    public List<LichSuDangNhapDTO> search(LichSuDangNhapSearchRequest request) {
        return repository.findAll(LichSuDangNhapSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<LichSuDangNhapDTO> search(LichSuDangNhapSearchRequest request, Pageable pageable) {
        return repository.findAll(LichSuDangNhapSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public LichSuDangNhapDTO saveRequest(LichSuDangNhapRequest request) {
        LichSuDangNhap ls = new LichSuDangNhap();
        BeanUtils.copyProperties(request, ls);
        return convertToDTO(repository.save(ls));
    }

    @Override
    public LichSuDangNhapDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch sử đăng nhập mã: " + id));
    }

    @Override
    public List<LichSuDangNhapDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LichSuDangNhapDTO convertToDTO(LichSuDangNhap entity) {
        LichSuDangNhapDTO dto = new LichSuDangNhapDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getUser() != null) {
            dto.setUserID(entity.getUser().getUserID());
            dto.setUsername(entity.getUser().getUsername());
        }
        return dto;
    }
}