package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.repository.user.KPIThuongPhatRepository;
import com.cuahangthucung.repository.user.KPIThuongPhatSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KPIThuongPhatServiceImpl extends BaseServiceImpl<KPIThuongPhat, Integer, KPIThuongPhatRepository> 
        implements KPIThuongPhatService {

    public KPIThuongPhatServiceImpl(KPIThuongPhatRepository repository) {
        super(repository);
    }

    @Override
    public List<KPIThuongPhatDTO> search(KPIThuongPhatSearchRequest request) {
        return repository.findAll(KPIThuongPhatSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<KPIThuongPhatDTO> search(KPIThuongPhatSearchRequest request, Pageable pageable) {
        return repository.findAll(KPIThuongPhatSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public KPIThuongPhatDTO saveRequest(KPIThuongPhatRequest request) {
        KPIThuongPhat kpi = (request.getMaKPI() != null)
                ? repository.findById(request.getMaKPI()).orElse(new KPIThuongPhat())
                : new KPIThuongPhat();

        BeanUtils.copyProperties(request, kpi, "nhanVien"); // ignore quan hệ
        return convertToDTO(repository.save(kpi));
    }

    @Override
    public KPIThuongPhatDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy KPI mã: " + id));
    }

    @Override
    public List<KPIThuongPhatDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KPIThuongPhatSummaryDTO getSummary(String thang) {
        BigDecimal tongThuong = repository.sumThuongByThang(thang);
        BigDecimal tongPhat = repository.sumPhatByThang(thang);

        return new KPIThuongPhatSummaryDTO(
                thang, 
                tongThuong, 
                tongPhat, 
                tongThuong.subtract(tongPhat), 
                0L
        );
    }

    private KPIThuongPhatDTO convertToDTO(KPIThuongPhat entity) {
        KPIThuongPhatDTO dto = new KPIThuongPhatDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            dto.setMaNV(entity.getNhanVien().getMaNV());
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }
        dto.setTongKet(entity.getThuong().subtract(entity.getPhat()));
        return dto;
    }
}