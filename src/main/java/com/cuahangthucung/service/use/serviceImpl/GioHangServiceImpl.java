package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.giohang.GioHangDTO;
import com.cuahangthucung.dto.use.giohang.GioHangRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSearchRequest;
import com.cuahangthucung.dto.use.giohang.GioHangSummaryDTO;
import com.cuahangthucung.entity.use.entity.GioHang;
import com.cuahangthucung.entity.use.entity.GioHangId;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.GioHangRepository;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository;
import com.cuahangthucung.repository.user.Interface.UserRepository;
import com.cuahangthucung.repository.use.Specification.GioHangSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.GioHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GioHangServiceImpl extends BaseServiceImpl<GioHang, GioHangId, GioHangRepository>
        implements GioHangService {

    private final SanPhamRepository sanPhamRepository;
    private final UserRepository userRepository;

    public GioHangServiceImpl(GioHangRepository repository,
                              SanPhamRepository sanPhamRepository,
                              UserRepository userRepository) {
        super(repository);
        this.sanPhamRepository = sanPhamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GioHangDTO> search(GioHangSearchRequest request) {
        return repository.findAll(GioHangSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GioHangDTO saveRequest(GioHangRequest request) {
        // ÉP BUỘC TUYỆT ĐỐI: Bỏ qua maGioHang truyền từ Frontend (nếu có).
        // Bắt buộc maGioHang phải đi theo maUser để đảm bảo mỗi user chỉ sở hữu duy nhất 1 giỏ.
        String maGioHang = "GH_" + request.getMaUser().trim();

        // Khởi tạo Khóa chính phức hợp với mã giỏ hàng đã được định danh theo User
        GioHangId id = new GioHangId(maGioHang, request.getMaSP());
        GioHang gioHang;

        // Kiểm tra xem sản phẩm này đã tồn tại trong giỏ hàng của User này chưa
        if (repository.existsById(id)) {
            gioHang = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng hợp lệ"));

            // CỘNG DỒN: Nếu đã có, tăng số lượng lên (Mỗi lần click thêm ở trang sản phẩm sẽ là +1)
            gioHang.setSoLuong(gioHang.getSoLuong() + request.getSoLuong());
        } else {
            // TẠO MỚI: Nếu sản phẩm chưa từng được User này thêm vào giỏ
            gioHang = new GioHang();
            gioHang.setId(id);
            gioHang.setSoLuong(request.getSoLuong()); // Mặc định nhận số lượng ban đầu (1 sản phẩm)
        }

        // Tìm kiếm thực thể User và SanPham để thiết lập mối quan hệ (Relationship)
        User user = userRepository.findById(Integer.parseInt(request.getMaUser().trim()))
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng có ID: " + request.getMaUser()));

        SanPham sanPham = sanPhamRepository.findById(request.getMaSP())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm có mã: " + request.getMaSP()));

        gioHang.setUser(user);
        gioHang.setSanPham(sanPham);

        // Lưu xuống Database bảng GIOHANG
        GioHang saved = repository.save(gioHang);

        // Chuyển đổi thành DTO trả về cho Frontend hiển thị
        return convertToDTO(saved);
    }
    @Override
    public GioHangDTO findByIdDTO(String maGioHang, String maSP) {
        GioHangId id = new GioHangId(maGioHang, maSP);
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giỏ hàng"));
    }

    @Override
    public List<GioHangDTO> findByMaGioHang(String maGioHang) {
        return repository.findByIdMaGioHang(maGioHang)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<GioHangDTO> findByMaUser(String maUser) {
        // Ép kiểu String từ Request/DTO sang Integer để tìm kiếm theo đúng userID của Entity
        Integer userIdInt = Integer.parseInt(maUser.trim());
        return repository.findByUserUserID(userIdInt)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BigDecimal tinhTongTien(String maGioHang) {
        return repository.tinhTongTienGioHang(maGioHang);
    }

    @Override
    public Integer countSoLuongSanPhamTrongGio(String maUser) {
        Integer userIdInt = Integer.parseInt(maUser.trim());
        return repository.countSoLuongSanPhamTrongGio(userIdInt);
    }

    @Override
    public GioHangSummaryDTO getSummary() {
        Long tongGioHang = (long) repository.findAll().size();
        Long tongSanPham = (long) repository.findAll().stream()
                .mapToInt(g -> g.getSoLuong() != null ? g.getSoLuong() : 0).sum();
        BigDecimal tongGiaTri = repository.tinhTongGiaTriTatCaGioHang();
        Long tongUser = repository.countSoUserDangCoGioHang();

        BigDecimal giaTrungBinh = tongGioHang > 0
                ? tongGiaTri.divide(BigDecimal.valueOf(tongGioHang), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        return new GioHangSummaryDTO(tongGioHang, tongSanPham, tongGiaTri, tongUser, giaTrungBinh);
    }

    @Override
    public boolean existsByUserAndSanPham(String maUser, String maSP) {
        Integer userIdInt = Integer.parseInt(maUser.trim());
        return repository.existsByUserUserIDAndIdMaSP(userIdInt, maSP);
    }

    @Override
    @Transactional
    public void deleteByMaGioHangAndMaSP(String maGioHang, String maSP) {
        GioHangId id = new GioHangId(maGioHang, maSP);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm trong giỏ hàng");
        }
        repository.deleteById(id);
    }

    @Override
    public GioHangDTO convertToDTO(GioHang entity) {
        GioHangDTO dto = new GioHangDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getId() != null) {
            dto.setMaGioHang(entity.getId().getMaGioHang());
            dto.setMaSP(entity.getId().getMaSP());
        }

        if (entity.getUser() != null) {
            dto.setMaUser(String.valueOf(entity.getUser().getUserID()));
            dto.setTenUser(entity.getUser().getUsername());
        }

        if (entity.getSanPham() != null) {
            dto.setTenSP(entity.getSanPham().getTenSP());
            dto.setDonGia(entity.getSanPham().getGia());

        }

        if (entity.getSoLuong() != null && entity.getSanPham() != null && entity.getSanPham().getGia() != null) {
            dto.setThanhTien(entity.getSanPham().getGia()
                    .multiply(BigDecimal.valueOf(entity.getSoLuong())));
        }

        return dto;
    }
}
