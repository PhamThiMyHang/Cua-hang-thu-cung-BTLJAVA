package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.NhanVienRepository;
import com.cuahangthucung.repository.user.NhanVienSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl extends BaseServiceImpl<NhanVien, Integer, NhanVienRepository> implements NhanVienService {

    public NhanVienServiceImpl(NhanVienRepository repository) {
        super(repository);
    }

    @Override
    public List<NhanVienDTO> search(NhanVienSearchRequest request) {
        return repository.findAll(NhanVienSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NhanVienDTO saveRequest(NhanVienRequest request) {
        NhanVien nv = (request.getMaNV() != null)
                ? repository.findById(request.getMaNV()).orElse(new NhanVien())
                : new NhanVien();

        BeanUtils.copyProperties(request, nv);
        return convertToDTO(repository.save(nv));
    }

    @Override
    public NhanVienDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên mã: " + id));
    }

    @Override
    public String generateNextMaNV() {
        // Chỉ đơn giản là lấy ID cuối + 1
        return repository.findLastNhanVien()
                .map(last -> String.valueOf(last.getMaNV() + 1))
                .orElse("1"); // Nếu chưa có nv nào thì bắt đầu từ 1
    }
    public List<NhanVienDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NhanVienSummaryDTO getSummary() {
        return new NhanVienSummaryDTO(
                repository.countTotalNhanVien(),
                repository.countStaff(),
                repository.countKTV(),
                repository.countNhanVienCoTaiKhoan()
        );
    }

    private NhanVienDTO convertToDTO(NhanVien entity) {
        NhanVienDTO dto = new NhanVienDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getChucVu() != null) {
            dto.setChucVu(entity.getChucVu().name());
        }

        if (entity.getUser() != null) {
            dto.setUserID(entity.getUser().getUserID());
            dto.setUsername(entity.getUser().getUsername());
        }

        return dto;
    }
}