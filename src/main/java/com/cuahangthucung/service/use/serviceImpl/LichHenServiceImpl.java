package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.lichhen.*;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.entity.use.entity.DichVu;
import com.cuahangthucung.entity.use.entity.LichHen;
import com.cuahangthucung.entity.use.enums.TrangThai;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.LichHenRepository;
import com.cuahangthucung.repository.use.Specification.LichHenSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.LichHenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LichHenServiceImpl extends BaseServiceImpl<LichHen, String, LichHenRepository>
        implements LichHenService {

    private static final String PREFIX_LICH_HEN = "LH";

    @PersistenceContext
    private EntityManager entityManager;

    public LichHenServiceImpl(LichHenRepository repository) {
        super(repository);
    }

    @Override
    public Page<LichHenDTO> search(LichHenSearchRequest request, Pageable pageable) {
        // ĐÃ SỬA: Kích hoạt truyền Specification bọc bộ lọc điều kiện tìm kiếm
        return repository.findAll(LichHenSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<LichHenDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public LichHenDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch hẹn: " + id));
    }

    @Override
    @Transactional
    public LichHenDTO saveRequest(LichHenRequest request) {
        LichHen lh;

        // 1. Kiểm tra kịch bản Cập Nhật hay Thêm Mới
        if (request.getMaLich() != null && !request.getMaLich().trim().isEmpty()) {
            lh = repository.findById(request.getMaLich())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch hẹn để cập nhật: " + request.getMaLich()));
        } else {
            lh = new LichHen();
            lh.setMaLich(phatSinhMaLichHen()); // Tự sinh mã tự tăng LH001
        }

        // 2. Map các thuộc tính cơ bản (bỏ qua các trường liên kết và trạng thái xử lý sau)
        BeanUtils.copyProperties(request, lh, "maLich", "trangThai");

        // 3. Thiết lập Trạng thái an toàn từ chuỗi String Request
        if (request.getTrangThai() != null && !request.getTrangThai().isBlank()) {
            try {
                lh.setTrangThai(TrangThai.valueOf(request.getTrangThai().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                if (lh.getMaLich() == null) lh.setTrangThai(TrangThai.PENDING);
            }
        }

        // 4. Liên kết các Thực thể bằng các tham chiếu Proxy (Tối ưu tài nguyên qua Reference)
        if (request.getMaKH() != null && !request.getMaKH().isBlank()) {
            lh.setKhachHang(entityManager.getReference(KhachHang.class, Integer.parseInt(request.getMaKH().trim())));
        }

        if (request.getMaPet() != null && !request.getMaPet().isBlank()) {
            lh.setPet(entityManager.getReference(Pet.class, request.getMaPet().trim()));
        } else {
            lh.setPet(null);
        }

        if (request.getMaNV() != null && !request.getMaNV().isBlank()) {
            lh.setNhanVien(entityManager.getReference(NhanVien.class, Integer.parseInt(request.getMaNV().trim())));
        }

        if (request.getMaDV() != null && !request.getMaDV().isBlank()) {
            lh.setDichVu(entityManager.getReference(DichVu.class, request.getMaDV().trim()));
        }

        return convertToDTO(repository.save(lh));
    }

    /**
     * ĐÃ SỬA: Trích xuất an toàn dữ liệu từ Object để gán vào thuộc tính phẳng của DTO
     */
    private LichHenDTO convertToDTO(LichHen entity) {
        if (entity == null) return null;

        LichHenDTO dto = new LichHenDTO();
        BeanUtils.copyProperties(entity, dto);

        // Gán thủ công chuỗi Trạng thái Enum
        if (entity.getTrangThai() != null) {
            dto.setTrangThai(entity.getTrangThai().name());
        }

        // Bóc tách dữ liệu Khách hàng
        if (entity.getKhachHang() != null) {
            dto.setMaKH(String.valueOf(entity.getKhachHang().getMaKH()));
            dto.setTenKH(entity.getKhachHang().getTenKH());
        }

        // Bóc tách dữ liệu Thú cưng
        if (entity.getPet() != null) {
            dto.setMaPet(String.valueOf(entity.getPet().getMaPet()));
            dto.setTenPet(entity.getPet().getTenPet());
        }

        // Bóc tách dữ liệu Nhân viên
        if (entity.getNhanVien() != null) {
            dto.setMaNV(String.valueOf(entity.getNhanVien().getMaNV()));
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }

        // Bóc tách dữ liệu Dịch vụ
        if (entity.getDichVu() != null) {
            dto.setMaDV(entity.getDichVu().getMaDV());
            dto.setTenDV(entity.getDichVu().getTenDV());
            dto.setGiaDV(entity.getDichVu().getGia());
        }

        return dto;
    }

    /**
     * Thuật toán tự phát sinh mã chuỗi tự tăng dạng LH001, LH002...
     */
    private String phatSinhMaLichHen() {
        Optional<LichHen> lastLH = repository.findLastLichHenByPrefix(PREFIX_LICH_HEN);
        if (lastLH.isEmpty()) {
            return PREFIX_LICH_HEN + "001";
        }

        String lastId = lastLH.get().getMaLich();
        try {
            String numberPart = lastId.substring(PREFIX_LICH_HEN.length());
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return PREFIX_LICH_HEN + String.format("%03d", nextNumber);
        } catch (Exception e) {
            return PREFIX_LICH_HEN + "_" + System.currentTimeMillis();
        }
    }

    @Override
    public BigDecimal tongDoanhThu() {
        return repository.tongDoanhThu();
    }

    @Override
    public List<DoanhThuNhanVienDTO> thongKeDoanhThuNhanVien() {
        return repository.thongKeDoanhThuNhanVien();
    }

    @Override
    public DoanhThuNhanVienDTO thongKeDoanhThuNhanVien(Integer maNV) {

        DoanhThuNhanVienDTO dto =
                repository.thongKeDoanhThuNhanVien(maNV);

        if(dto == null){
            dto = new DoanhThuNhanVienDTO();
            dto.setMaNV(maNV);
            dto.setSoLichHoanThanh(0L);
            dto.setDoanhThu(BigDecimal.ZERO);
        }

        return dto;
    }

    @Override
    public LichHenSummaryDTO getSummary() {
        return repository.layThongKeTongQuanLichHen();
    }
}