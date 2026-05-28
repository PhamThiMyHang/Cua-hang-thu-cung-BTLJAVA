package com.cuahangthucung.service.user.serviceImpl;

import com.cuahangthucung.dto.user.User.UserDTO;
import com.cuahangthucung.dto.user.User.UserRequest;
import com.cuahangthucung.dto.user.User.UserSearchRequest;
import com.cuahangthucung.dto.user.User.UserSummaryDTO;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.entity.user.enums.LoaiKH;
import com.cuahangthucung.entity.user.enums.UserStatus;
import com.cuahangthucung.repository.user.Interface.RoleRepository;
import com.cuahangthucung.repository.user.Interface.UserRepository;
import com.cuahangthucung.repository.user.Interface.NhanVienRepository;
import com.cuahangthucung.repository.user.Interface.KhachHangRepository;
import com.cuahangthucung.repository.user.Specification.UserSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer, UserRepository> implements UserService {

    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;
    // Thêm các biến này vào đây
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Sửa constructor để inject đầy đủ
    public UserServiceImpl(UserRepository repository,
                           NhanVienRepository nhanVienRepository,
                           KhachHangRepository khachHangRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        super(repository);
        this.userRepository = repository;
        this.nhanVienRepository = nhanVienRepository;
        this.khachHangRepository = khachHangRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> search(UserSearchRequest request) {
        return repository.findAll(UserSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> search(UserSearchRequest request, Pageable pageable) {
        return repository.findAll(UserSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public UserDTO saveRequest(UserRequest request) {
        User user = (request.getUserID() != null)
                ? repository.findById(request.getUserID()).orElse(new User())
                : new User();

        // Copy các trường tương thích (bao gồm cả gmail mới)
        BeanUtils.copyProperties(request, user, "roles");

        if (request.getRoleNames() != null && !request.getRoleNames().isEmpty()) {
            user.getRoles().clear();
            // TODO: Xử lý gán Role dựa trên roleNames ở đây
        }

        return convertToDTO(repository.save(user));
    }

    // ====================== LIÊN KẾT USER - NHÂN VIÊN (ĐÃ SỬA SANG 1-1) ======================
    @Override
    @Transactional
    public UserDTO linkNhanVien(Integer userID, Integer maNV) {
        User user = repository.findById(userID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User mã: " + userID));

        NhanVien nhanVien = nhanVienRepository.findById(maNV)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Nhân viên mã: " + maNV));

        // Thiết lập mối quan hệ 1-1 trực tiếp thay vì add vào List
        nhanVien.setUser(user);
        user.setNhanVien(nhanVien);

        nhanVienRepository.save(nhanVien);
        return convertToDTO(repository.save(user));
    }

    // ====================== LIÊN KẾT USER - KHÁCH HÀNG (ĐÃ SỬA SANG 1-1) ======================
    @Override
    @Transactional
    public UserDTO linkKhachHang(Integer userID, Integer maKH) {
        User user = repository.findById(userID)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User mã: " + userID));

        KhachHang khachHang = khachHangRepository.findById(maKH)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Khách hàng mã: " + maKH));

        // Thiết lập mối quan hệ 1-1 trực tiếp thay vì add vào List
        khachHang.setUser(user);
        user.setKhachHang(khachHang);

        khachHangRepository.save(khachHang);
        return convertToDTO(repository.save(user));
    }

    @Override
    public UserDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User mã: " + id));
    }

    @Override
    public List<UserDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserSummaryDTO getSummary() {
        return new UserSummaryDTO(
                repository.countTotalUsers(),
                repository.countByStatus(com.cuahangthucung.entity.user.enums.UserStatus.ACTIVE),
                repository.countByStatus(com.cuahangthucung.entity.user.enums.UserStatus.INACTIVE),
                repository.countUsersHaveNhanVien(),
                repository.countUsersHaveKhachHang()
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByGmail(String gmail) {
        return repository.existsByGmail(gmail);
    }

    @Override
    public java.util.Optional<User> findByGmail(String gmail) {
        return repository.findByGmail(gmail);
    }


    @Override
    public UserDTO registerCustomer(UserRequest request) {
        // 1. Kiểm tra gmail đã tồn tại chưa
        if (userRepository.existsByGmail(request.getGmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // 2. Tạo User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setGmail(request.getGmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        // Gán Role (Cần đảm bảo bạn có hàm findByRoleName trong RoleRepository)


        System.out.println("Đang tìm kiếm role: CUSTOMER");
        Role customerRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Chưa cấu hình Role CUSTOMER"));
        user.setRoles(Set.of(customerRole));

        User savedUser = userRepository.save(user);

        // 3. Tự động tạo Khách hàng liên kết
        KhachHang khachHang = new KhachHang();
        khachHang.setTenKH(request.getUsername());
        khachHang.setGmail(request.getGmail());
        khachHang.setSdt(request.getSoDienThoai()); // CẦN THÊM DÒNG NÀY
        khachHang.setDiaChi(request.getDiaChi());           // CẦN THÊM DÒNG NÀY
        khachHang.setLoaiKH(LoaiKH.THUONG);
        khachHang.setDiemTichLuy(0);
        khachHang.setUser(savedUser);

        khachHangRepository.save(khachHang);

        return findByIdDTO(savedUser.getUserID()); // Gọi hàm chuyển đổi DTO có sẵn của bạn
    }


    // ====================== CONVERT TO DTO (ĐÃ CHỈNH SỬA) ======================
    private UserDTO convertToDTO(User entity) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto); // Copy username, password, userID, gmail

        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name());
        }

        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream()
                    .map(role -> role.getRoleName())
                    .collect(Collectors.toSet()));
        }

        // Đọc dữ liệu từ quan hệ 1-1 an toàn (Tránh NullPointerException)
        if (entity.getNhanVien() != null) {
            NhanVien nv = entity.getNhanVien();
            dto.setMaNV(nv.getMaNV());
            dto.setTenNV(nv.getTenNV());
            dto.setGmailNV(nv.getGmail()); // Map Gmail riêng của Nhân viên
        }

        if (entity.getKhachHang() != null) {
            KhachHang kh = entity.getKhachHang();
            dto.setMaKH(kh.getMaKH());
            dto.setTenKH(kh.getTenKH());
            dto.setGmailKH(kh.getGmail()); // Map Gmail riêng của Khách hàng
        }

        return dto;
    }
}