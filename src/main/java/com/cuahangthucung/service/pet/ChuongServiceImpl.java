package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.pet.ChuongRepository;
import com.cuahangthucung.repository.pet.ChuongSpecification;
import com.cuahangthucung.repository.pet.LoaiChuongRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ChuongServiceImpl extends BaseServiceImpl<Chuong, String, ChuongRepository>
        implements ChuongService {

    private final LoaiChuongRepository loaiChuongRepository;

    // Spring sẽ tự động Inject cả 2 Repository qua Constructor này
    public ChuongServiceImpl(ChuongRepository repository, LoaiChuongRepository loaiChuongRepository) {
        super(repository);
        this.loaiChuongRepository = loaiChuongRepository;
    }

    @Override
    public List<ChuongDTO> search(ChuongSearchRequest request) {
        return repository.findAll(ChuongSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChuongDTO saveRequest(ChuongRequest request) {
        Chuong chuong;

        // 1. Kiểm tra thêm mới hay cập nhật
        if (request.getMaChuong() != null && !request.getMaChuong().trim().isEmpty()) {
            chuong = repository.findById(request.getMaChuong())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chuồng mã: " + request.getMaChuong()));
        } else {
            chuong = new Chuong();
            String newId = generateNextMaChuong(); // Sinh mã (VD: C001)
            chuong.setMaChuong(newId);
        }

        // 2. Copy dữ liệu cơ bản (TenChuong, TrangThai, MoTa)
        // Lưu ý: Không copy "loaiChuong" vì ta sẽ map thủ công qua ID
        BeanUtils.copyProperties(request, chuong, "maChuong", "loaiChuong", "danhSachPet");

        // 3. Mapping Quan hệ: Loại Chuồng (LoaiChuong)
        if (request.getMaLoaiChuong() != null) {
            LoaiChuong loai = loaiChuongRepository.findById(request.getMaLoaiChuong())
                    .orElseThrow(() -> new ResourceNotFoundException("Loại chuồng không tồn tại: " + request.getMaLoaiChuong()));

            chuong.setLoaiChuong(loai);
        }

        // 4. Logic bổ sung (Nếu cần):
        // Ví dụ: Nếu chuồng mới tạo mặc định là TRONG
        if (chuong.getTrangThai() == null) {
            chuong.setTrangThai(TrangThaiChuong.TRONG);
        }

        // 5. Lưu vào Database
        Chuong saved = repository.save(chuong);

        // 6. Trả về DTO
        return convertToDTO(saved);
    }

    @Override
    public ChuongSummaryDTO getSummary() {
        Long tong = repository.count();
        Long trong = repository.countByTrangThai(TrangThaiChuong.TRONG);
        Long kin = repository.countByTrangThai(TrangThaiChuong.KIN);
        Long suaChua = repository.countByTrangThai(TrangThaiChuong.SUA_CHUA);

        Double tyLe = (tong > 0) ? (double) kin / tong * 100 : 0.0;

        Map<String, Long> thongKeLoai = repository.countByLoaiChuongGrouped().stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0], // Tên loại
                        obj -> (Long) obj[1]    // Số lượng
                ));

        return new ChuongSummaryDTO(tong, trong, kin, suaChua, tyLe, thongKeLoai);
    }

    @Override
    public ChuongDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuồng mã: " + id));
    }

    @Override
    public List<ChuongDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ChuongDTO convertToDTO(Chuong chuong) {
        ChuongDTO dto = new ChuongDTO();
        BeanUtils.copyProperties(chuong, dto);

        if (chuong.getTrangThai() != null) {
            dto.setTrangThai(chuong.getTrangThai().name());
        }

        if (chuong.getLoaiChuong() != null) {
            dto.setMaLoaiChuong(chuong.getLoaiChuong().getMaLoaiChuong());
            dto.setTenLoaiChuong(chuong.getLoaiChuong().getTenLoai());
        }

        // Đếm số lượng pet từ danh sách quan hệ
        dto.setSoLuongPetHienTai(chuong.getDanhSachPet() != null ? chuong.getDanhSachPet().size() : 0);

        return dto;
    }
    public String generateNextMaChuong() {
        String maxId = repository.findMaxMaChuong(); // Kết quả ví dụ: "C015"
        if (maxId == null) {
            return "C001"; // Mã đầu tiên nếu bảng trống
        }

        // Tách phần số ra (bỏ chữ 'C'), tăng lên 1, sau đó format lại
        int nextNumber = Integer.parseInt(maxId.substring(1)) + 1;
        return String.format("C%03d", nextNumber); // Kết quả: "C016"
    }

}