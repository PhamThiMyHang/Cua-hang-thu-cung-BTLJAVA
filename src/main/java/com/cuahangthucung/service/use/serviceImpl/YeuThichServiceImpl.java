package com.cuahangthucung.service.use.serviceImpl;


import com.cuahangthucung.dto.use.yeuthich.YeuThichDTO;
import com.cuahangthucung.dto.use.yeuthich.YeuThichRequest;
import com.cuahangthucung.dto.use.yeuthich.YeuThichSearchRequest;
import com.cuahangthucung.entity.use.entity.SanPham;
import com.cuahangthucung.entity.use.entity.YeuThich;
import com.cuahangthucung.entity.use.entity.YeuThichId;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.exception.ResourceNotFoundException;
import com.cuahangthucung.repository.use.Interface.SanPhamRepository;
import com.cuahangthucung.repository.use.Interface.YeuThichRepository;
import com.cuahangthucung.repository.user.Interface.UserRepository;
import com.cuahangthucung.repository.use.Specification.YeuThichSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import com.cuahangthucung.service.use.service.YeuThichService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YeuThichServiceImpl extends BaseServiceImpl<YeuThich, YeuThichId, YeuThichRepository>
        implements YeuThichService {

    private final SanPhamRepository sanPhamRepository;
    private final UserRepository userRepository;

    public YeuThichServiceImpl(YeuThichRepository repository,
                               SanPhamRepository sanPhamRepository,
                               UserRepository userRepository) {
        super(repository);
        this.sanPhamRepository = sanPhamRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<YeuThichDTO> search(YeuThichSearchRequest request) {
        return repository.findAll(YeuThichSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public YeuThichDTO saveRequest(YeuThichRequest request) {
        // 1. Ép kiểu và kiểm tra tính hợp lệ của mã người dùng ngay từ đầu
        Integer maUserInt;
        try {
            maUserInt = Integer.parseInt(request.getMaUser().trim());
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException("Mã người dùng không hợp lệ: " + request.getMaUser());
        }

        // 2. Kiểm tra xem đã tồn tại trong DB chưa
        YeuThichId id = new YeuThichId(maUserInt, request.getMaSP());
        if (repository.existsById(id)) {
            throw new RuntimeException("Sản phẩm đã có trong danh sách yêu thích của bạn");
        }

        // 3. Tìm kiếm User và Sản phẩm
        User user = userRepository.findById(maUserInt)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng: " + request.getMaUser()));

        SanPham sanPham = sanPhamRepository.findById(request.getMaSP())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + request.getMaSP()));

        // 4. Khởi tạo đối tượng YeuThich
        YeuThich yeuThich = new YeuThich();
        yeuThich.setId(id);
        yeuThich.setUser(user);
        yeuThich.setSanPham(sanPham);

        // 5. Lưu và trả về
        YeuThich saved = repository.save(yeuThich);
        return convertToDTO(saved);
    }
    @Override
    public List<YeuThichDTO> findByMaUser(String maUser) {
        Integer userIdInt = Integer.parseInt(maUser.trim()); // Quan trọng
        return repository.findByIdMaUser(userIdInt)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUserAndSanPham(String maUser, String maSP) {
        Integer userIdInt = Integer.parseInt(maUser.trim());
        return repository.existsByIdMaUserAndIdMaSP(userIdInt, maSP);
    }

    @Override
    @Transactional
    public void deleteByUserAndSanPham(String maUser, String maSP) {
        Integer userIdInt = Integer.parseInt(maUser.trim());
        // SỬA: Dùng Integer userIdInt thay vì String.valueOf
        YeuThichId id = new YeuThichId(userIdInt, maSP);

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm trong danh sách yêu thích");
        }
        repository.deleteById(id);
    }

    @Override
    public YeuThichDTO convertToDTO(YeuThich entity) {
        YeuThichDTO dto = new YeuThichDTO();

        if (entity.getId() != null) {
            dto.setMaUser(String.valueOf(entity.getId().getMaUser()));
            dto.setMaSP(entity.getId().getMaSP());
        }

        if (entity.getSanPham() != null) {
            dto.setTenSP(entity.getSanPham().getTenSP());
            dto.setGia(entity.getSanPham().getGia());

        }

        return dto;
    }
}