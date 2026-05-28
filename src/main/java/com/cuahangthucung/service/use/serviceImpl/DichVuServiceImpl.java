package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.dichvu.DichVuDTO;
import com.cuahangthucung.dto.use.dichvu.DichVuRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSearchRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSummaryDTO;
import com.cuahangthucung.entity.use.entity.DichVu;
import com.cuahangthucung.repository.use.Interface.DichVuRepository;
import com.cuahangthucung.repository.use.Specification.DichVuSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.DichVuService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DichVuServiceImpl extends BaseServiceImpl<DichVu, String, DichVuRepository> implements DichVuService {

    public DichVuServiceImpl(DichVuRepository repository) {
        super(repository);
    }

    @Override
    public Page<DichVuDTO> search(DichVuSearchRequest request, Pageable pageable) {
        return repository.findAll(DichVuSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public DichVuDTO saveRequest(DichVuRequest request) {
        DichVu dichVu = (request.getMaDV() != null && !request.getMaDV().isBlank())
                ? repository.findById(request.getMaDV()).orElse(new DichVu())
                : new DichVu();

        BeanUtils.copyProperties(request, dichVu);

        if (dichVu.getMaDV() == null || dichVu.getMaDV().isBlank()) {
            dichVu.setMaDV(generateNextMaDV());
        }

        return convertToDTO(repository.save(dichVu));
    }

    @Override
    public DichVuDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với mã: " + id));
    }

    @Override
    public List<DichVuDTO> findAllDTO() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DichVuSummaryDTO getSummary() {
        return repository.getSummaryData();
    }

    @Override
    public String generateNextMaDV() {
        String prefix = "DV" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        return repository.findLastDichVuByPrefix(prefix)
                .map(last -> {
                    int lastNum = Integer.parseInt(last.getMaDV().substring(prefix.length()));
                    return String.format("%s%02d", prefix, lastNum + 1);
                })
                .orElse(prefix + "01");
    }

    @Override
    public boolean isTenDVExists(String tenDV) {
        return repository.existsByTenDV(tenDV);
    }

    @Override
    public List<DichVuDTO> findByGiaRange(double giaMin, double giaMax) {
        return repository.findByGiaRange(giaMin, giaMax)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ĐÃ CHỈNH SỬA: Điền dữ liệu logic thực tế cho DTO bổ sung
    private DichVuDTO convertToDTO(DichVu entity) {
        if (entity == null) return null;

        DichVuDTO dto = new DichVuDTO();
        BeanUtils.copyProperties(entity, dto);

        // 1. Đếm số lần dịch vụ được sử dụng dựa trên kích thước danh sách đặt lịch liên kết
        if (entity.getDanhSachLichHen() != null) {
            dto.setSoLanSuDung((long) entity.getDanhSachLichHen().size());
        } else {
            dto.setSoLanSuDung(0L);
        }

        // 2. Mặc định dịch vụ luôn hoạt động nếu tồn tại trong hệ thống (hoặc tùy biến theo logic của bạn)
        dto.setDangHoatDong(true);

        return dto;
    }
}