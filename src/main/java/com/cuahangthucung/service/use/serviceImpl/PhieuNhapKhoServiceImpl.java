package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoDTO;
import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoRequest;
import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuNhapKho;
import com.cuahangthucung.entity.use.entity.PhieuNhapKhoId;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.PhieuNhapKhoRepository;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository; // Import đúng Repository cụ thể
import com.cuahangthucung.repository.use.Specification.PhieuNhapKhoSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.PhieuNhapKhoService;
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
public class PhieuNhapKhoServiceImpl
        extends BaseServiceImpl<PhieuNhapKho, PhieuNhapKhoId, PhieuNhapKhoRepository>
        implements PhieuNhapKhoService {

    // SỬA LỖI: Thay JpaRepository generic bằng SanPhamRepository cụ thể của bạn
    private final SanPhamRepository sanPhamRepository;

    public PhieuNhapKhoServiceImpl(PhieuNhapKhoRepository repository,
                                   SanPhamRepository sanPhamRepository) {
        super(repository);
        this.sanPhamRepository = sanPhamRepository;
    }

    @Override
    public Page<PhieuNhapKhoDTO> search(PhieuNhapKhoSearchRequest request, Pageable pageable) {
        return repository.findAll(PhieuNhapKhoSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<PhieuNhapKhoDTO> findByMaPhieu(String maPhieu) {
        return repository.findByMaPhieu(maPhieu).stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PhieuNhapKhoDTO findById(String maPhieu, String maSP) {
        PhieuNhapKhoId id = new PhieuNhapKhoId(maPhieu, maSP);
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dữ liệu dòng phiếu nhập kho yêu cầu."));
    }

    @Override
    @Transactional
    public PhieuNhapKhoDTO saveRequest(PhieuNhapKhoRequest request) {
        // 1. Xác thực sự tồn tại của SanPham trước khi liên kết dữ liệu khóa chính kép
        SanPham sanPham = sanPhamRepository.findById(request.getMaSP())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy mã sản phẩm: " + request.getMaSP()));

        String maPhieuXuLy = request.getMaPhieu();

        // Tự động sinh mã phiếu tăng dần nếu Frontend gửi lên giá trị trống hoặc chuỗi "AUTO"
        boolean isUpdate = (maPhieuXuLy != null && !maPhieuXuLy.trim().isEmpty() && !maPhieuXuLy.equalsIgnoreCase("AUTO"));

        if (!isUpdate) {
            maPhieuXuLy = phatSinhMaPhieuNhap();
        }

        PhieuNhapKhoId id = new PhieuNhapKhoId(maPhieuXuLy, request.getMaSP());
        Optional<PhieuNhapKho> existingPnOpt = repository.findById(id);

        PhieuNhapKho pn;

        if (existingPnOpt.isPresent()) {
            // Trường hợp cập nhật dòng chi tiết cũ của phiếu nhập
            pn = existingPnOpt.get();

            // Tìm các thuộc tính rỗng/null để tránh ghi đè dữ liệu cũ
            String[] ignoreProperties = getNullPropertyNames(request);
            BeanUtils.copyProperties(request, pn, ignoreProperties);
        } else {
            // Trường hợp tạo mới dòng chi tiết của phiếu nhập
            pn = new PhieuNhapKho();
            BeanUtils.copyProperties(request, pn, "maPhieu", "maSP");

            pn.setMaPhieu(maPhieuXuLy);
            pn.setSanPham(sanPham); // Đính kèm thực thể để Hibernate tự gán vào khóa chính kép

            if (pn.getSoLuong() == null) {
                pn.setSoLuong(0);
            }
            if (pn.getNgayNhap() == null) {
                pn.setNgayNhap(LocalDate.now()); // Mặc định là ngày hôm nay nếu tạo mới không truyền
            }
        }

        return convertToDTO(repository.save(pn));
    }

    /**
     * CHUYỂN ĐỔI SANG DTO THEO HƯỚNG 1:
     * Lấy đơn giá nhập từ bảng SanPham để điền vào DTO và tính toán thành tiền.
     */
    private PhieuNhapKhoDTO convertToDTO(PhieuNhapKho entity) {
        if (entity == null) return null;

        PhieuNhapKhoDTO dto = new PhieuNhapKhoDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getSanPham() != null) {
            dto.setMaSP(entity.getSanPham().getMaSP());
            dto.setTenSP(entity.getSanPham().getTenSP());

            // LẤY ĐƠN GIÁ TỪ BẢNG SANPHAM
            BigDecimal giaNhapTuSanPham = entity.getSanPham().getGia();
            dto.setDonGia(giaNhapTuSanPham);

            // TÍNH TOÁN THÀNH TIỀN: SoLuong (trong PhieuNhapKho) * Gia (trong SanPham)
            if (giaNhapTuSanPham != null && dto.getSoLuong() != null) {
                BigDecimal soLuongBd = BigDecimal.valueOf(dto.getSoLuong());
                dto.setThanhTien(giaNhapTuSanPham.multiply(soLuongBd));
            } else {
                dto.setThanhTien(BigDecimal.ZERO);
            }

            // Lấy thông tin nhà cung cấp liên kết từ sản phẩm
            if (entity.getSanPham().getNhaCungCap() != null) {
                dto.setMaNCC(entity.getSanPham().getNhaCungCap().getMaNCC());
                dto.setTenNCC(entity.getSanPham().getNhaCungCap().getTenNCC());
            }
        } else {
            dto.setThanhTien(BigDecimal.ZERO);
        }

        return dto;
    }

    /**
     * Thuật toán sinh mã phiếu nhập tự động tăng theo cấu trúc: PN + YYMM + Số thứ tự 3 số
     * Ví dụ chạy trong năm 2026: PN2605001, PN2605002...
     */
    private String phatSinhMaPhieuNhap() {
        String prefix = "PN" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        Optional<PhieuNhapKho> lastPn = repository.findLastPhieuNhapByPrefix(prefix);

        if (lastPn.isEmpty()) {
            return prefix + "001";
        }

        String lastId = lastPn.get().getMaPhieu();
        try {
            String numberPart = lastId.substring(prefix.length());
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return prefix + String.format("%03d", nextNumber);
        } catch (Exception e) {
            return prefix + "_" + System.currentTimeMillis();
        }
    }

    /**
     * Hàm bổ trợ tìm thuộc tính null hoặc chuỗi trống từ Request.
     * Đảm bảo an toàn khi cập nhật một vài trường tùy ý mà không làm hỏng dữ liệu cũ.
     */
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