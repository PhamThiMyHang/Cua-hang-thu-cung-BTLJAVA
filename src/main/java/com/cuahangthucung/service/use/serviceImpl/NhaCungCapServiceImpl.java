package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapDTO;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapRequest;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapSearchRequest;
import com.cuahangthucung.entity.use.entity.NhaCungCap;
import com.cuahangthucung.entity.use.entity.SanPham; // Giả định Entity SanPham nằm ở đây
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.NhaCungCapRepository;
import com.cuahangthucung.repository.use.Specification.NhaCungCapSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.NhaCungCapService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NhaCungCapServiceImpl extends BaseServiceImpl<NhaCungCap, String, NhaCungCapRepository>
        implements NhaCungCapService {

    private static final String PREFIX_NCC = "NCC";

    public NhaCungCapServiceImpl(NhaCungCapRepository repository) {
        super(repository);
    }

    @Override
    public Page<NhaCungCapDTO> search(NhaCungCapSearchRequest request, Pageable pageable) {
        // Đstream/map chuẩn hóa: Kích hoạt Specification bọc bộ lọc điều kiện
        return repository.findAll(NhaCungCapSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<NhaCungCapDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public NhaCungCapDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp: " + id));
    }

    @Override
    @Transactional
    public NhaCungCapDTO saveRequest(NhaCungCapRequest request) {
        NhaCungCap ncc;

        // Kiểm tra tạo mới hay cập nhật
        if (request.getMaNCC() != null && !request.getMaNCC().trim().isEmpty()) {
            ncc = repository.findById(request.getMaNCC())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp để cập nhật: " + request.getMaNCC()));
        } else {
            ncc = new NhaCungCap();
            ncc.setMaNCC(phatSinhMaNhaCungCap()); // Tự sinh mã tăng dần NCC001
        }

        BeanUtils.copyProperties(request, ncc, "maNCC");
        return convertToDTO(repository.save(ncc));
    }

    /**
     * Map thủ công thông tin thống kê từ danh sách thực thể liên kết Lazy Loading
     */
    private NhaCungCapDTO convertToDTO(NhaCungCap entity) {
        if (entity == null) return null;

        NhaCungCapDTO dto = new NhaCungCapDTO();
        BeanUtils.copyProperties(entity, dto);

        // Kiểm tra bộ danh sách sản phẩm để trích xuất thông tin phẳng cho DTO hiển thị
        if (entity.getDanhSachSanPham() != null) {
            dto.setSoSanPhamCungCap((long) entity.getDanhSachSanPham().size());

            // Lấy danh sách tên sản phẩm (optional)
            List<String> tenSanPhams = entity.getDanhSachSanPham().stream()
                    .map(SanPham::getTenSP) // Thay đổi getter tương ứng theo Entity SanPham của bạn
                    .collect(Collectors.toList());
            dto.setDanhSachSanPham(tenSanPhams);
        } else {
            dto.setSoSanPhamCungCap(0L);
            dto.setDanhSachSanPham(new ArrayList<>());
        }

        return dto;
    }

    /**
     * Thuật toán phát sinh mã tự động tăng chuỗi (NCC001, NCC002, ...)
     */
    private String phatSinhMaNhaCungCap() {
        Optional<NhaCungCap> lastNCC = repository.findLastNhaCungCapByPrefix(PREFIX_NCC);
        if (lastNCC.isEmpty()) {
            return PREFIX_NCC + "001";
        }

        String lastId = lastNCC.get().getMaNCC();
        try {
            String numberPart = lastId.substring(PREFIX_NCC.length());
            int nextNumber = Integer.parseInt(numberPart) + 1;
            return PREFIX_NCC + String.format("%03d", nextNumber);
        } catch (Exception e) {
            return PREFIX_NCC + "_" + System.currentTimeMillis();
        }
    }
}