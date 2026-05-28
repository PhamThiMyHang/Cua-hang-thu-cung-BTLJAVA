package com.cuahangthucung.service.user.serviceImpl;

import com.cuahangthucung.dto.user.NhanVien.NhanVienDTO;
import com.cuahangthucung.dto.user.NhanVien.NhanVienRequest;
import com.cuahangthucung.dto.user.NhanVien.NhanVienSearchRequest;
import com.cuahangthucung.dto.user.NhanVien.NhanVienSummaryDTO;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.user.Interface.NhanVienRepository;
import com.cuahangthucung.repository.user.Specification.NhanVienSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.user.service.NhanVienService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl extends BaseServiceImpl<NhanVien, Integer, NhanVienRepository>
        implements NhanVienService {

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
    public Page<NhanVienDTO> search(NhanVienSearchRequest request, Pageable pageable) {
        return repository.findAll(
                NhanVienSpecification.getFilter(request),
                pageable
        ).map(this::convertToDTO);
    }

    @Override
    @Transactional
    public NhanVienDTO saveRequest(NhanVienRequest request) {
        // Kiểm tra trùng lặp Gmail khi tạo mới hoặc thay đổi gmail khác hiện tại
        if (request.getMaNV() == null) {
            if (repository.existsByGmail(request.getGmail())) {
                throw new RuntimeException("Gmail nhân viên này đã tồn tại trong hệ thống!");
            }
        } else {
            NhanVien currentNv = repository.findById(request.getMaNV())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên mã: " + request.getMaNV()));
            if (!currentNv.getGmail().equalsIgnoreCase(request.getGmail())
                    && repository.existsByGmail(request.getGmail())) {
                throw new RuntimeException("Gmail nhân viên mới cập nhật đã được sử dụng!");
            }
        }

        NhanVien nv = (request.getMaNV() != null)
                ? repository.findById(request.getMaNV()).orElse(new NhanVien())
                : new NhanVien();

        // Loại bỏ sao chép các mối quan hệ Object phức tạp
        BeanUtils.copyProperties(request, nv, "user", "danhSachChamCong", "danhSachLichTruc", "danhSachKPI");

        return convertToDTO(repository.save(nv));
    }

    @Override
    public NhanVienDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên mã: " + id));
    }

    @Override
    public List<NhanVienDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByGmail(String gmail) {
        return repository.existsByGmail(gmail);
    }

    @Override
    public String generateNextMaNV() {
        return repository.findLastNhanVien()
                .map(last -> String.valueOf(last.getMaNV() + 1))
                .orElse("1");
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

    // ====================== CONVERT TO DTO (ĐÃ ĐỒNG BỘ GMAIL) ======================
    private NhanVienDTO convertToDTO(NhanVien entity) {
        NhanVienDTO dto = new NhanVienDTO();
        BeanUtils.copyProperties(entity, dto); // Sao chép maNV, tenNV, sdt, diaChi, gmail

        if (entity.getChucVu() != null) {
            dto.setChucVu(entity.getChucVu().name());
        }

        // Lấy thông tin tài khoản liên kết (Mối quan hệ 1-1 an toàn)
        if (entity.getUser() != null) {
            dto.setUserID(entity.getUser().getUserID());
            dto.setUsername(entity.getUser().getUsername());
            dto.setGmailUser(entity.getUser().getGmail()); // Map Gmail của tài khoản User liên kết
        }

        return dto;
    }
}