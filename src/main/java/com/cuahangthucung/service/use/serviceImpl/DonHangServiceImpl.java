package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.chitietdonhang.ChiTietDonHangDTO;
import com.cuahangthucung.dto.use.donhang.DonHangDTO;
import com.cuahangthucung.dto.use.donhang.DonHangRequest;
import com.cuahangthucung.dto.use.donhang.DonHangSearchRequest;
import com.cuahangthucung.dto.use.donhang.DonHangSummaryDTO;
import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.enums.TrangThai;

// ĐÃ BỔ SUNG: Import chính xác thực thể KhachHang và NhanVien từ package .user.entity
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.NhanVien;

import com.cuahangthucung.repository.use.Interface.DonHangRepository;
import com.cuahangthucung.repository.use.Specification.DonHangSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.DonHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonHangServiceImpl extends BaseServiceImpl<DonHang, String, DonHangRepository> implements DonHangService {

    public DonHangServiceImpl(DonHangRepository repository) {
        super(repository);
    }

    @Override
    public Page<DonHangDTO> search(DonHangSearchRequest request, Pageable pageable) {
        return repository.findAll(DonHangSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public DonHangDTO saveRequest(DonHangRequest request) {
        DonHang donHang = (request.getMaDH() != null && !request.getMaDH().isBlank())
                ? repository.findById(request.getMaDH()).orElse(new DonHang())
                : new DonHang();

        // Loại bỏ bỏ qua map tự động cho các trường liên kết phức tạp hoặc khác kiểu dữ liệu
        BeanUtils.copyProperties(request, donHang, "trangThai", "khachHang", "nhanVien");

        // 1. ĐÃ SỬA DÒNG 55: Chuyển đổi mã chuỗi String nhận từ request sang số Integer để gán cho Object KhachHang
        if (request.getMaKH() != null && !request.getMaKH().isBlank()) {
            KhachHang kh = new KhachHang();
            kh.setMaKH(Integer.parseInt(request.getMaKH().trim()));
            donHang.setKhachHang(kh);
        } else {
            donHang.setKhachHang(null);
        }

        // 2. ĐÃ SỬA DÒNG 62: Chuyển đổi mã chuỗi String nhận từ request sang số Integer để gán cho Object NhanVien
        if (request.getMaNV() != null && !request.getMaNV().isBlank()) {
            NhanVien nv = new NhanVien();
            nv.setMaNV(Integer.parseInt(request.getMaNV().trim()));
            donHang.setNhanVien(nv);
        } else {
            donHang.setNhanVien(null);
        }

        // 3. Ép kiểu trạng thái đơn hàng từ String sang Enum
        if (request.getTrangThai() != null) {
            try {
                donHang.setTrangThai(TrangThai.valueOf(request.getTrangThai().toUpperCase()));
            } catch (IllegalArgumentException e) {
                donHang.setTrangThai(TrangThai.PENDING);
            }
        }

        if (donHang.getMaDH() == null || donHang.getMaDH().isBlank()) {
            donHang.setMaDH(generateNextMaDH());
        }

        if (donHang.getNgayTao() == null) {
            donHang.setNgayTao(LocalDate.now());
        }

        return convertToDTO(repository.save(donHang));
    }

    @Override
    public DonHangDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + id));
    }

    @Override
    public List<DonHangDTO> findAllDTO() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DonHangSummaryDTO getSummary() {
        LocalDate now = LocalDate.now();
        LocalDate dauThang = now.withDayOfMonth(1);
        Double doanhThuRaw = repository.tinhDoanhThuTheoKhoangThoiGian(dauThang, now);
        BigDecimal doanhThu = (doanhThuRaw != null) ? BigDecimal.valueOf(doanhThuRaw) : BigDecimal.ZERO;

        return new DonHangSummaryDTO(
                repository.count(),
                repository.countByTrangThai(TrangThai.PENDING),
                repository.countByTrangThai(TrangThai.DONE),
                repository.countByTrangThai(TrangThai.CANCEL),
                doanhThu
        );
    }

    @Override
    public String generateNextMaDH() {
        String prefix = "DH" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        return repository.findLastDonHangByPrefix(prefix)
                .map(last -> {
                    int lastNum = Integer.parseInt(last.getMaDH().substring(prefix.length()));
                    return String.format("%s%02d", prefix, lastNum + 1);
                })
                .orElse(prefix + "01");
    }

    @Override
    @Transactional
    public DonHangDTO capNhatTrangThai(String maDH, TrangThai trangThai) {
        DonHang donHang = repository.findById(maDH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với mã: " + maDH));
        donHang.setTrangThai(trangThai);
        return convertToDTO(repository.save(donHang));
    }

    @Override
    public List<DonHangDTO> findByMaKH(String maKH) {
        try {
            Integer id = Integer.parseInt(maKH.trim());
            return repository.findByKhachHang_MaKH(id) // Gọi tên hàm mới của Repo
                    .stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu maKH truyền vào không phải là số
        }
    }

    @Override
    public List<DonHangDTO> findByMaNV(String maNV) {
        try {
            Integer id = Integer.parseInt(maNV.trim());
            return repository.findByNhanVien_MaNV(id) // Gọi tên hàm mới của Repo
                    .stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DonHangDTO> findByNgayTaoRange(LocalDate tuNgay, LocalDate denNgay) {
        return repository.findByNgayTaoBetween(tuNgay, denNgay)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Double tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) {
        return repository.tinhDoanhThuTheoKhoangThoiGian(tuNgay, denNgay);
    }

    // ────────────────────────────────────────────────────────────────
    // Helper: Chuyển đổi Entity sang DTO để trả dữ liệu về giao diện
    // ────────────────────────────────────────────────────────────────
    private DonHangDTO convertToDTO(DonHang entity) {
        if (entity == null) return null;
        DonHangDTO dto = new DonHangDTO();
        // Copy các thuộc tính cơ bản giống nhau (maDH, ngayTao, tongTien...)
        BeanUtils.copyProperties(entity, dto, "khachHang", "nhanVien");

        // 1. Nếu đơn hàng có thông tin khách hàng -> Bốc cả ID và Tên bỏ vào DTO
        if (entity.getKhachHang() != null) {
            dto.setMaKH(String.valueOf(entity.getKhachHang().getMaKH()));
            dto.setTenKH(entity.getKhachHang().getTenKH()); // <-- Đoạn này nạp Tên Khách Hàng
        }

        // 2. Nếu đơn hàng có thông tin nhân viên phụ trách -> Bốc cả ID và Tên bỏ vào DTO
        if (entity.getNhanVien() != null) {
            dto.setMaNV(String.valueOf(entity.getNhanVien().getMaNV()));
            dto.setTenNV(entity.getNhanVien().getTenNV()); // <-- Đoạn này nạp Tên Nhân Viên
        }

        // 3. Đồng bộ hóa kiểu chuỗi trạng thái đơn hàng
        if (entity.getTrangThai() != null) {
            dto.setTrangThai(entity.getTrangThai().name());
        }

        // 4. Ánh xạ danh sách chi tiết đơn hàng
        if (entity.getDanhSachChiTiet() != null && !entity.getDanhSachChiTiet().isEmpty()) {
            List<ChiTietDonHangDTO> listDetails = entity.getDanhSachChiTiet().stream().map(ct -> {
                ChiTietDonHangDTO ctDto = new ChiTietDonHangDTO();
                BeanUtils.copyProperties(ct, ctDto);
                ctDto.setMaDH(entity.getMaDH());
                if (ct.getSanPham() != null) {
                    ctDto.setMaSP(ct.getSanPham().getMaSP());
                    ctDto.setTenSP(ct.getSanPham().getTenSP());
                }
                if (ct.getSoLuong() != null && ct.getDonGia() != null) {
                    ctDto.setThanhTien(ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong())));
                }
                return ctDto;
            }).collect(Collectors.toList());
            dto.setChiTietDonHang(listDetails);
        } else {
            dto.setChiTietDonHang(new ArrayList<>());
        }

        return dto;
    }
}