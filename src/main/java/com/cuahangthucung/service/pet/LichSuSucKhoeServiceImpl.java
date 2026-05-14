package com.cuahangthucung.service.pet;

<<<<<<< HEAD
import com.cuahangthucung.dto.pet.LichSuSearchRequest;
import com.cuahangthucung.dto.pet.LichSuSucKhoeDTO;
import com.cuahangthucung.dto.pet.LichSuSucKhoeRequest;
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.entity.pet.enums.LoaiLichSu;
import com.cuahangthucung.repository.pet.LichSuSpecification;
import com.cuahangthucung.repository.pet.LichSuSucKhoeRepository;
import com.cuahangthucung.repository.pet.PetRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
=======
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.repository.pet.LichSuSucKhoeRepository;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
>>>>>>> DoanThiNgocGiau

@Service
public class LichSuSucKhoeServiceImpl extends BaseServiceImpl<LichSuSucKhoe, Integer, LichSuSucKhoeRepository>
        implements LichSuSucKhoeService {
<<<<<<< HEAD

    private final PetRepository petRepository;

    public LichSuSucKhoeServiceImpl(LichSuSucKhoeRepository repository, PetRepository petRepository) {
        super(repository);
        this.petRepository = petRepository;
    }

    /**
     * Đáp ứng yêu cầu tìm kiếm 1-8.
     * Filter sẽ tự động lọc theo maPet, loai, ngay có trong request.
     */
    @Override
    public List<LichSuSucKhoeDTO> search(LichSuSearchRequest request) {
        return repository.findAll(LichSuSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Đáp ứng 8 yêu cầu count (tính số bản ghi).
     * Sử dụng cùng một Specification với hàm search để đảm bảo tính đồng bộ.
     */
    @Override
    public long countByRequest(LichSuSearchRequest request) {
        return repository.count(LichSuSpecification.getFilter(request));
    }

    @Override
    public LichSuSucKhoeDTO findByIdDTO(Integer id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch sử sức khỏe mã: " + id));
    }

    @Override
    @Transactional
    public LichSuSucKhoeDTO saveRequest(LichSuSucKhoeRequest request) {
        LichSuSucKhoe entity = (request.getMaLS() != null)
                ? repository.findById(request.getMaLS()).orElse(new LichSuSucKhoe())
                : new LichSuSucKhoe();

        BeanUtils.copyProperties(request, entity);

        // Ép kiểu Enum an toàn để tránh lỗi ứng dụng khi gửi string sai
        try {
            entity.setLoai(LoaiLichSu.valueOf(request.getLoai()));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Loại lịch sử không hợp lệ: " + request.getLoai());
        }

        var pet = petRepository.findById(request.getMaPet())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thú cưng mã: " + request.getMaPet()));
        entity.setPet(pet);

        return convertToDTO(repository.save(entity));
    }

    /**
     * Hàm convert an toàn, kiểm tra null cho các quan hệ (pet)
     * để tránh lỗi NullPointerException khi lấy dữ liệu.
     */
    private LichSuSucKhoeDTO convertToDTO(LichSuSucKhoe entity) {
        if (entity == null) return null;

        LichSuSucKhoeDTO dto = new LichSuSucKhoeDTO();
        BeanUtils.copyProperties(entity, dto);

        // Kiểm tra quan hệ pet để lấy maPet và tenPet
        if (entity.getPet() != null) {
            dto.setMaPet(entity.getPet().getMaPet());
            dto.setTenPet(entity.getPet().getTenPet());
        }

        // Kiểm tra enum loai
        if (entity.getLoai() != null) {
            dto.setLoai(entity.getLoai().name());
        }

        return dto;
    }
=======
    public LichSuSucKhoeServiceImpl(LichSuSucKhoeRepository repository) { super(repository); }
>>>>>>> DoanThiNgocGiau
}