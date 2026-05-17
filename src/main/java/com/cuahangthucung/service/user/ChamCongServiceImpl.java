package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.ChamCongRepository;
import com.cuahangthucung.repository.user.ChamCongSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
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
        // KẾT HỢP NHÂN VIÊN: Tạo liên kết gián tiếp để tránh lỗi null khóa ngoại khi lưu
        if (request.getMaNV() != null) {
            NhanVien nvProxy = new NhanVien();
            nvProxy.setMaNV(request.getMaNV());
            cc.setNhanVien(nvProxy);
        }
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
        ChamCongSummaryDTO summary = new ChamCongSummaryDTO();

        // Lấy danh sách chấm công trong khoảng thời gian
        List<ChamCong> danhSach = repository.findByNgayBetween(request.getTuNgay(), request.getDenNgay());

        long tongSoNgayChamCong = danhSach.size();

        // Tính tổng số ngày thực tế có đi làm (GioVao không null)
        long soNgayDiLam = danhSach.stream()
                .filter(c -> c.getGioVao() != null)
                .count();

        // Tính tỷ lệ đi làm
        double tyLeDiLam = tongSoNgayChamCong > 0 ? ((double) soNgayDiLam / tongSoNgayChamCong) * 100 : 0.0;

        summary.setTongSoNgayChamCong(tongSoNgayChamCong);
        summary.setSoNgayDiLam(soNgayDiLam);
        summary.setTyLeDiLam(Math.round(tyLeDiLam * 100.0) / 100.0); // Làm tròn 2 chữ số
        return summary;
    }

    @Override
    public ChamCongDTO convertToDTO(ChamCong entity) {
        ChamCongDTO dto = new ChamCongDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            dto.setMaNV(entity.getNhanVien().getMaNV());
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }
        // 2. TÍNH ĐỘNG SỐ GIỜ LÀM (Chỉ tính trên DTO, không lưu DB)
        if (entity.getGioVao() != null && entity.getGioRa() != null) {
            Duration duration = Duration.between(entity.getGioVao(), entity.getGioRa());
            double hours = duration.toMinutes() / 60.0;
            dto.setSoGioLam((int) Math.round(hours)); // Đúc về Integer theo đúng kiểu dữ liệu trong ChamCongDTO của bạn
        } else {
            dto.setSoGioLam(0);
        }
        // 3. TÍNH ĐỘNG TRẠNG THÁI CHẤM CÔNG
        if (entity.getGioVao() == null) {
            dto.setTrangThaiChamCong("VẮNG");
        } else if (entity.getGioVao().isAfter(LocalTime.of(8, 5))) {
            dto.setTrangThaiChamCong("MUỘN");
        } else {
            dto.setTrangThaiChamCong("ĐÚNG GIỜ");
        }
        return dto;
    }
}