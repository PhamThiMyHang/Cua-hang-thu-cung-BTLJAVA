package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.repository.user.ChamCongRepository;
import com.cuahangthucung.repository.user.ChamCongSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChamCongServiceImpl extends BaseServiceImpl<ChamCong, Integer, ChamCongRepository> 
        implements ChamCongService {

    public ChamCongServiceImpl(ChamCongRepository repository) {
        super(repository);
    }

    @Override
    public List<ChamCongDTO> search(ChamCongSearchRequest request) {
        return repository.findAll(ChamCongSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChamCongDTO> search(ChamCongSearchRequest request, Pageable pageable) {
        return repository.findAll(ChamCongSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public ChamCongDTO saveRequest(ChamCongRequest request) {
        ChamCong cc = (request.getMaCC() != null)
                ? repository.findById(request.getMaCC()).orElse(new ChamCong())
                : new ChamCong();

        BeanUtils.copyProperties(request, cc, "nhanVien"); // ignore quan hệ để tránh lỗi
        return convertToDTO(repository.save(cc));
    }

    @Override
    public ChamCongDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chấm công mã: " + id));
    }

    @Override
    public List<ChamCongDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChamCongSummaryDTO getSummary(ChamCongSearchRequest request) {
        // TODO: Có thể triển khai logic thống kê thực tế sau
        return new ChamCongSummaryDTO();
    }

    @Override
    public ChamCongDTO convertToDTO(ChamCong entity) {
        ChamCongDTO dto = new ChamCongDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            dto.setMaNV(entity.getNhanVien().getMaNV());
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }
        return dto;
    }
}