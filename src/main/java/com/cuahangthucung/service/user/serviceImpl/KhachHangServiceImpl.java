package com.cuahangthucung.service.user.serviceImpl;

import com.cuahangthucung.dto.user.khachhang.KhachHangDTO;
import com.cuahangthucung.dto.user.khachhang.KhachHangRequest;
import com.cuahangthucung.dto.user.khachhang.KhachHangSearchRequest;
import com.cuahangthucung.dto.user.khachhang.KhachHangSummaryDTO;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.user.Interface.KhachHangRepository;
import com.cuahangthucung.repository.user.Specification.KhachHangSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.user.service.KhachHangService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl extends BaseServiceImpl<KhachHang, Integer, KhachHangRepository>
        implements KhachHangService {

    public KhachHangServiceImpl(KhachHangRepository repository) {
        super(repository);
    }

    @Override
    public List<KhachHangDTO> search(KhachHangSearchRequest request) {
        return repository.findAll(KhachHangSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<KhachHangDTO> search(KhachHangSearchRequest request, Pageable pageable) {
        return repository.findAll(KhachHangSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public KhachHangDTO saveRequest(KhachHangRequest request) {
        // 1. Kiểm tra trùng lặp Gmail khách hàng
        if (request.getMaKH() == null) {
            if (repository.existsByGmail(request.getGmail())) {
                throw new RuntimeException("Gmail khách hàng này đã tồn tại trong hệ thống!");
            }
        } else {
            KhachHang currentKh = repository.findById(request.getMaKH())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng mã: " + request.getMaKH()));
            if (!currentKh.getGmail().equalsIgnoreCase(request.getGmail())
                    && repository.existsByGmail(request.getGmail())) {
                throw new RuntimeException("Gmail khách hàng thay đổi mới đã được sử dụng!");
            }
        }

        KhachHang kh = (request.getMaKH() != null)
                ? repository.findById(request.getMaKH()).orElse(new KhachHang())
                : new KhachHang();

        // Sao chép các trường cơ bản từ Request sang Entity (bỏ qua liên kết Object)
        BeanUtils.copyProperties(request, kh, "user");

        // Gán Proxy User để tránh việc mất thông tin tài khoản khi lưu/cập nhật dữ liệu
        if (request.getUserID() != null) {
            User userProxy = new User();
            userProxy.setUserID(request.getUserID());
            kh.setUser(userProxy);
        } else {
            kh.setUser(null);
        }

        return convertToDTO(repository.save(kh));
    }

    @Override
    public KhachHangDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng mã: " + id));
    }

    @Override
    public List<KhachHangDTO> findAllDTO() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByGmail(String gmail) {
        return repository.existsByGmail(gmail);
    }

    @Override
    public KhachHangSummaryDTO getSummary() {
        return new KhachHangSummaryDTO(
                repository.countTotalKhachHang(),
                repository.countKhachVIP(),
                repository.countKhachThuong(), // Đã tối ưu bằng hàm viết sẵn thay vì làm toán trừ
                repository.sumDiemTichLuy()
        );
    }

    @Override
    public String generateNextMaKH() {
        return repository.findLastKhachHang()
                .map(last -> String.valueOf(last.getMaKH() + 1))
                .orElse("1");
    }

    // ====================== CONVERT TO DTO (ĐÃ ĐỒNG BỘ GMAIL) ======================
    private KhachHangDTO convertToDTO(KhachHang entity) {
        KhachHangDTO dto = new KhachHangDTO();
        BeanUtils.copyProperties(entity, dto); // Sao chép maKH, tenKH, sdt, diaChi, diemTichLuy, gmail

        if (entity.getLoaiKH() != null) {
            dto.setLoaiKH(entity.getLoaiKH().name());
        }

        // Lấy thông tin tài khoản liên kết (Mối quan hệ 1-1 an toàn)
        if (entity.getUser() != null) {
            dto.setUserID(entity.getUser().getUserID());
            dto.setUsername(entity.getUser().getUsername());
            dto.setGmailUser(entity.getUser().getGmail()); // Map Gmail của tài khoản User liên kết vào DTO
        }

        return dto;
    }
}