package com.cuahangthucung.service.use.serviceImpl;

import com.cuahangthucung.dto.use.sanpham.SanPhamDTO;
import com.cuahangthucung.dto.use.sanpham.SanPhamRequest;
import com.cuahangthucung.dto.use.sanpham.SanPhamSearchRequest;
import com.cuahangthucung.dto.use.sanpham.SanPhamSummaryDTO;
import com.cuahangthucung.entity.use.entity.NhaCungCap;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.entity.use.entity.ViTriSanPham;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.NhaCungCapRepository;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository;
import com.cuahangthucung.repository.use.Interface.ViTriSanPhamRepository;
import com.cuahangthucung.repository.use.Interface.YeuThichRepository;
import com.cuahangthucung.repository.use.Specification.SanPhamSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.SanPhamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl extends BaseServiceImpl<SanPham, String, SanPhamRepository>
        implements SanPhamService {

    private final NhaCungCapRepository nhaCungCapRepository;
    private final YeuThichRepository yeuThichRepository;
    private final ViTriSanPhamRepository viTriRepository;

    public SanPhamServiceImpl(SanPhamRepository repository,
                              NhaCungCapRepository nhaCungCapRepository,
                              YeuThichRepository yeuThichRepository,
                              ViTriSanPhamRepository viTriRepository) {
        super(repository);
        this.nhaCungCapRepository = nhaCungCapRepository;
        this.yeuThichRepository = yeuThichRepository;
        this.viTriRepository = viTriRepository;
    }

    @Override
    public Page<SanPhamDTO> search(SanPhamSearchRequest request, Pageable pageable) {
        return repository.findAll(SanPhamSpecification.getFilter(request), pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<SanPhamDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public SanPhamDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với mã: " + id));
    }

    @Override
    @Transactional
    public SanPhamDTO saveRequest(SanPhamRequest request) {
        SanPham sp;
        boolean isUpdate = (request.getMaSP() != null && !request.getMaSP().trim().isEmpty());

        if (isUpdate) {
            // Trường hợp Cập nhật sản phẩm cũ
            sp = repository.findById(request.getMaSP().trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm cần cập nhật với mã: " + request.getMaSP()));

            // Tìm ra danh sách các trường bằng null hoặc rỗng từ Request gửi lên
            String[] ignoreProperties = getNullPropertyNames(request);

            // Copy đè những trường có dữ liệu, trường nào null/rỗng thì bỏ qua (giữ nguyên giá trị cũ trong DB)
            BeanUtils.copyProperties(request, sp, ignoreProperties);

        } else {
            // Trường hợp Thêm mới hoàn toàn -> Tự sinh mã theo chuẩn SP001, SP002...
            sp = new SanPham();
            sp.setMaSP(generateNextMaSP());

            // Đối với hàng mới, copy toàn bộ thuộc tính, loại trừ maSP
            BeanUtils.copyProperties(request, sp, "maSP");

            // Nếu không truyền số lượng mặc định sẽ khởi tạo bằng 0
            if (sp.getSoLuong() == null) {
                sp.setSoLuong(0);
            }
        }

        // Xử lý map thực thể quan hệ Nhà Cung Cấp
        if (request.getMaNCC() != null && !request.getMaNCC().trim().isEmpty()) {
            NhaCungCap ncc = nhaCungCapRepository.findById(request.getMaNCC().trim())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp với mã: " + request.getMaNCC()));
            sp.setNhaCungCap(ncc);
        } else if (!isUpdate) {
            // Khi tạo mới thì bắt buộc phải có Nhà cung cấp
            throw new IllegalArgumentException("Mã nhà cung cấp không được để trống khi lưu sản phẩm");
        }
        // Lưu ý: Nếu là Update và request.getMaNCC() bị trống, khối logic trên sẽ bỏ qua và giữ nguyên Nhà cung cấp cũ

        // Xử lý vị trí
        if (request.getViTri() != null &&
                !request.getViTri().trim().isEmpty()) {

            ViTriSanPham vt = viTriRepository
                    .findById(request.getViTri().trim())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Không tìm thấy vị trí: "
                                            + request.getViTri()));

            sp.setViTriSanPham(vt);
        }

        return convertToDTO(repository.save(sp));
    }

    @Override
    public String generateNextMaSP() {
        String prefix = "SP";
        Optional<SanPham> lastProductOpt = repository.findLastSanPhamByPrefix(prefix);

        if (lastProductOpt.isEmpty()) {
            return prefix + "001";
        }

        String lastMaSP = lastProductOpt.get().getMaSP();
        try {
            String numberPart = lastMaSP.substring(prefix.length());
            long nextNumber = Long.parseLong(numberPart) + 1;

            return prefix + String.format("%03d", nextNumber);
        } catch (Exception e) {
            return prefix + System.currentTimeMillis();
        }
    }

    /* --- HIỆN THỰC HÀM LẤY THỐNG KÊ KHO SẢN PHẨM --- */
    @Override
    public SanPhamSummaryDTO getSummary(Integer nguongCanhBao) {
        // Nếu không truyền hoặc truyền giá trị vô lý, mặc định ngưỡng sắp hết hàng là 5
        int nguong = (nguongCanhBao == null || nguongCanhBao < 0) ? 5 : nguongCanhBao;

        Long tongSoSanPham = repository.countTotalSanPham();
        Long soHetHang = repository.countHetHang();
        Long soSapHetHang = repository.countSapHetHang(nguong);
        BigDecimal tongGiaTriKho = repository.tinhTongGiaTriKho();

        return new SanPhamSummaryDTO(tongSoSanPham, soHetHang, soSapHetHang, tongGiaTriKho);
    }
    /**
     * Hàm bổ trợ quét qua SanPhamRequest để tìm các thuộc tính bị null hoặc chuỗi trống.
     * Trả về mảng tên thuộc tính để truyền vào tham số ignoreProperties của BeanUtils.
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        // Luôn luôn giữ vững mã sản phẩm cũ, không cho phép sao chép đè trường này
        emptyNames.add("maSP");

        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // Nếu trường nhận về từ Frontend là null hoặc là chuỗi String rỗng (chỉ có khoảng trắng)
            if (srcValue == null || (srcValue instanceof String && ((String) srcValue).trim().isEmpty())) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }

    // --- Cập nhật phương thức convert hoặc dùng hàm bổ trợ ---
    // Cách tối ưu: Tạo phương thức helper để set trạng thái yêu thích cho List DTO
    public void populateIsLikedStatus(List<SanPhamDTO> dtos, Integer maUser) {
        if (maUser == null) {
            dtos.forEach(dto -> dto.setLiked(false));
            return;
        }

        // Lấy danh sách maSP đã thích của User (Cần thêm hàm này vào YeuThichRepository)
        // Ví dụ: List<String> listMaSP = yeuThichRepository.findListMaSPByMaUser(maUser);
        Set<String> setLiked = new HashSet<>(yeuThichRepository.findListMaSPByMaUser(maUser));

        dtos.forEach(dto -> dto.setLiked(setLiked.contains(dto.getMaSP())));
    }


    private SanPhamDTO convertToDTO(SanPham entity) {
        if (entity == null) return null;

        SanPhamDTO dto = new SanPhamDTO();
        BeanUtils.copyProperties(entity, dto);


        if (entity.getNhaCungCap() != null) {
            dto.setMaNCC(entity.getNhaCungCap().getMaNCC());
            dto.setTenNCC(entity.getNhaCungCap().getTenNCC());
        }

        if(entity.getViTriSanPham()!=null){
            dto.setViTri(
                    entity.getViTriSanPham().getMaViTri()
            );
            dto.setTenViTri(entity.getViTriSanPham().getViTri());
        }
        return dto;
    }

    @Override
    public long countByViTri(String maViTri) {
        return repository.countByViTriSanPham_MaViTri(maViTri);
    }
}