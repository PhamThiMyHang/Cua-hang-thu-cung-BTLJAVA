package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiDTO;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiRequest;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiSearchRequest;
import com.cuahangthucung.entity.use.entity.KhuyenMai;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.KhuyenMaiRepository;
import com.cuahangthucung.repository.use.Specification.KhuyenMaiSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.KhuyenMaiService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServiceImpl extends BaseServiceImpl<KhuyenMai, String, KhuyenMaiRepository>
        implements KhuyenMaiService {

    private static final String MA_KM_PREFIX = "KM";

    public KhuyenMaiServiceImpl(KhuyenMaiRepository repository) {
        super(repository);
    }

    @Override
    public Page<KhuyenMaiDTO> search(KhuyenMaiSearchRequest request, Pageable pageable) {
        return repository.findAll(KhuyenMaiSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<KhuyenMaiDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public KhuyenMaiDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khuyến mãi: " + id));
    }

    @Override
    @Transactional
    public KhuyenMaiDTO saveRequest(KhuyenMaiRequest request) {
        KhuyenMai km;

        // Nếu truyền lên mã KM cũ -> Thực hiện Cập nhật (Update)
        if (request.getMaKM() != null && !request.getMaKM().trim().isEmpty()) {
            km = repository.findById(request.getMaKM())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khuyến mãi để cập nhật: " + request.getMaKM()));
        } else {
            // Nếu không truyền mã KM -> Thực hiện Thêm mới (Create) + Tự sinh mã tăng dần dạng KM001
            km = new KhuyenMai();
            km.setMaKM(phatSinhMaKhuyenMai());
        }

        BeanUtils.copyProperties(request, km, "maKM"); // Không copy đè mã KM sang Entity
        return convertToDTO(repository.save(km));
    }

    /**
     * Hàm phụ trợ nạp trạng thái hiệu lực động dựa theo ngày hiện tại
     */
    private KhuyenMaiDTO convertToDTO(KhuyenMai entity) {
        if (entity == null) return null;

        KhuyenMaiDTO dto = new KhuyenMaiDTO();
        BeanUtils.copyProperties(entity, dto);

        // Tính toán conHieuLuc động: ngày bắt đầu <= ngày hiện tại <= ngày kết thúc
        LocalDate homNay = LocalDate.now();
        boolean checkHieuLuc = (entity.getNgayBD() != null && entity.getNgayKT() != null)
                && !homNay.isBefore(entity.getNgayBD())
                && !homNay.isAfter(entity.getNgayKT());

        dto.setConHieuLuc(checkHieuLuc);
        return dto;
    }

    /**
     * Thuật toán tự sinh mã tăng dần (KM001, KM002, ...)
     */
    private String phatSinhMaKhuyenMai() {
        Optional<KhuyenMai> lastKM = repository.findLastKhuyenMaiByPrefix(MA_KM_PREFIX);
        if (lastKM.isEmpty()) {
            return MA_KM_PREFIX + "001";
        }

        String lastMaKM = lastKM.get().getMaKM(); // Ví dụ: "KM015"
        try {
            String soThuTuStr = lastMaKM.substring(MA_KM_PREFIX.length()); // Cắt chuỗi lấy phần số "015"
            int soThuTuTiepTheo = Integer.parseInt(soThuTuStr) + 1; // Tăng lên 1 thành 16
            return MA_KM_PREFIX + String.format("%03d", soThuTuTiepTheo); // Định dạng lại thành "KM016"
        } catch (Exception e) {
            return MA_KM_PREFIX + "_" + System.currentTimeMillis(); // Phương án dự phòng nếu mã cũ lỗi định dạng số
        }
    }
}