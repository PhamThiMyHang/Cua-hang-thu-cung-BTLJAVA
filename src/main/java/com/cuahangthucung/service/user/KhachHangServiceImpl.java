package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.repository.user.KhachHangRepository;
import com.cuahangthucung.repository.user.KhachHangSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl extends BaseServiceImpl<KhachHang, Integer, KhachHangRepository> 
        implements KhachHangService {

    public KhachHangServiceImpl(KhachHangRepository repository) {
        super(repository);
    }

    @Override
    public List<KhachHangDTO> search(KhachHangSearchRequest request) {
        return repository.findAll(KhachHangSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<KhachHangDTO> search(KhachHangSearchRequest request, Pageable pageable) {
        return repository.findAll(KhachHangSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public KhachHangDTO saveRequest(KhachHangRequest request) {
        KhachHang kh = (request.getMaKH() != null)
                ? repository.findById(request.getMaKH()).orElse(new KhachHang())
                : new KhachHang();

        BeanUtils.copyProperties(request, kh, "user"); // ignore quan hệ
        return convertToDTO(repository.save(kh));
    }

    @Override
    public KhachHangDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng mã: " + id));
    }

    @Override
    public List<KhachHangDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public KhachHangSummaryDTO getSummary() {
        return new KhachHangSummaryDTO(
                repository.countTotalKhachHang(),
                repository.countKhachVIP(),
                repository.count() - repository.countKhachVIP(),
                repository.sumDiemTichLuy()
        );
    }

    private KhachHangDTO convertToDTO(KhachHang entity) {
        KhachHangDTO dto = new KhachHangDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getLoaiKH() != null) {
            dto.setLoaiKH(entity.getLoaiKH().name());
        }

        if (entity.getUser() != null) {
            dto.setUserID(entity.getUser().getUserID());
            dto.setUsername(entity.getUser().getUsername());
        }

        return dto;
    }

    @Override
    public String generateNextMaKH() {
        return repository.findLastKhachHang()
                .map(last -> String.valueOf(last.getMaKH() + 1))
                .orElse("1");
    }
}