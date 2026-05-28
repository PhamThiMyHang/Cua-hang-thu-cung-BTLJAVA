package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoDTO;
import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoRequest;
import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuXuatKho;
import com.cuahangthucung.entity.use.entity.PhieuXuatKhoId;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.PhieuXuatKhoRepository;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository;
import com.cuahangthucung.repository.user.Interface.NhanVienRepository;
import com.cuahangthucung.repository.use.Specification.PhieuXuatKhoSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.PhieuXuatKhoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhieuXuatKhoServiceImpl
        extends BaseServiceImpl<PhieuXuatKho, PhieuXuatKhoId, PhieuXuatKhoRepository>
        implements PhieuXuatKhoService {

    private final SanPhamRepository sanPhamRepository;
    private final NhanVienRepository nhanVienRepository;

    public PhieuXuatKhoServiceImpl(PhieuXuatKhoRepository repository,
                                   SanPhamRepository sanPhamRepository,
                                   NhanVienRepository nhanVienRepository) {
        super(repository);
        this.sanPhamRepository = sanPhamRepository;
        this.nhanVienRepository = nhanVienRepository;
    }

    @Override
    public Page<PhieuXuatKhoDTO> search(PhieuXuatKhoSearchRequest request, Pageable pageable) {
        return repository.findAll(PhieuXuatKhoSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<PhieuXuatKhoDTO> findByMaPhieu(String maPhieu) {
        return repository.findByMaPhieu(maPhieu).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PhieuXuatKhoDTO findById(String maPhieu, String maSP) {
        PhieuXuatKhoId id = new PhieuXuatKhoId(maPhieu, maSP);
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu xuất kho với mã: " + maPhieu + ", sản phẩm: " + maSP));
    }

    @Override
    @Transactional
    public PhieuXuatKhoDTO saveRequest(PhieuXuatKhoRequest request) {
        SanPham sanPham = sanPhamRepository.findById(request.getMaSP().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm có mã: " + request.getMaSP()));

        String maPhieuXuLy = request.getMaPhieu();
        boolean isUpdate = (maPhieuXuLy != null && !maPhieuXuLy.trim().isEmpty() && !maPhieuXuLy.equalsIgnoreCase("AUTO"));

        if (!isUpdate) {
            maPhieuXuLy = phatSinhMaPhieuXuat();
        }

        PhieuXuatKhoId id = new PhieuXuatKhoId(maPhieuXuLy, request.getMaSP().trim());
        Optional<PhieuXuatKho> existingPxOpt = repository.findById(id);

        PhieuXuatKho px;

        if (existingPxOpt.isPresent()) {
            px = existingPxOpt.get();
            String[] ignoreProperties = getNullPropertyNames(request);
            BeanUtils.copyProperties(request, px, ignoreProperties);
        } else {
            px = new PhieuXuatKho();
            BeanUtils.copyProperties(request, px, "maPhieu", "maSP");
            px.setMaPhieu(maPhieuXuLy);
            px.setSanPham(sanPham);

            if (px.getSoLuong() == null) {
                px.setSoLuong(1);
            }
            if (px.getNgayXuat() == null) {
                px.setNgayXuat(LocalDate.now());
            }
        }

        // SỬA LỖI: Ép kiểu String maNV từ request sang Integer để khớp với NhanVienRepository<NhanVien, Integer>
        if (request.getMaNV() != null && !request.getMaNV().trim().isEmpty()) {
            try {
                Integer maNVInt = Integer.parseInt(request.getMaNV().trim());
                NhanVien nv = nhanVienRepository.findById(maNVInt)
                        .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhân viên với mã số: " + maNVInt));
                px.setNhanVien(nv);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Mã nhân viên phải là một chuỗi định dạng số nguyên hợp lệ!");
            }
        }

        return convertToDTO(repository.save(px));
    }

    private PhieuXuatKhoDTO convertToDTO(PhieuXuatKho entity) {
        if (entity == null) return null;

        PhieuXuatKhoDTO dto = new PhieuXuatKhoDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getNhanVien() != null) {
            // Chuyển đổi ngược mã NV từ Integer trong Entity sang String phẳng trong DTO công bố ra ngoài
            dto.setMaNV(String.valueOf(entity.getNhanVien().getMaNV()));
            dto.setTenNV(entity.getNhanVien().getTenNV());
        }

        if (entity.getSanPham() != null) {
            dto.setMaSP(entity.getSanPham().getMaSP());
            dto.setTenSP(entity.getSanPham().getTenSP());

            BigDecimal giaXuat = entity.getSanPham().getGia();
            dto.setDonGia(giaXuat);

            if (giaXuat != null && dto.getSoLuong() != null) {
                BigDecimal soLuongBd = BigDecimal.valueOf(dto.getSoLuong());
                dto.setThanhTien(giaXuat.multiply(soLuongBd));
            } else {
                dto.setThanhTien(BigDecimal.ZERO);
            }
        } else {
            dto.setThanhTien(BigDecimal.ZERO);
        }

        return dto;
    }

    private String phatSinhMaPhieuXuat() {
        String prefix = "PX" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        Optional<PhieuXuatKho> lastPx = repository.findLastPhieuXuatByPrefix(prefix);

        if (lastPx.isEmpty()) {
            return prefix + "001";
        }

        String lastId = lastPx.get().getMaPhieu();
        try {
            String numberPart = lastId.substring(prefix.length());
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return prefix + String.format("%03d", nextNumber);
        } catch (Exception e) {
            return prefix + "_" + System.currentTimeMillis();
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        emptyNames.add("maPhieu");
        emptyNames.add("maSP");

        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || (srcValue instanceof String && ((String) srcValue).trim().isEmpty())) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}