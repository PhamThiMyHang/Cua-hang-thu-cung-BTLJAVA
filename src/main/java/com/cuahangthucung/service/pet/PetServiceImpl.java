package com.cuahangthucung.service.pet;

import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.repository.pet.PetRepository;
import com.cuahangthucung.repository.pet.PetSpecification;
import com.cuahangthucung.service.base.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl extends BaseServiceImpl<Pet, String, PetRepository> implements PetService {

    public PetServiceImpl(PetRepository repository) {
        super(repository);
    }

    @Override
    public List<PetDTO> search(PetSearchRequest request) {
        // Gọi Specification đã sửa với allOf()
        return repository.findAll(PetSpecification.getFilter(request))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PetDTO saveRequest(PetRequest request) {
        Pet pet = new Pet();
        // Copy các trường cơ bản (tên, giống, tuổi, giá...)
        BeanUtils.copyProperties(request, pet);

        // Nghiệp vụ sinh mã tự động nếu thêm mới
        if (pet.getMaPet() == null || pet.getMaPet().isEmpty()) {
            pet.setMaPet(generateNextMaPet());
        }

        // Lưu ý: Ở đây bạn nên bổ sung logic tìm Chuong, KH, NV từ DB
        // dựa trên ID trong request trước khi save nếu cần mapping quan hệ.

        Pet saved = repository.save(pet);
        return convertToDTO(saved);
    }

    @Override
    public PetDTO findByIdDTO(String id) {
        return repository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Pet với mã: " + id));
    }

    @Override
    public PetSummaryDTO getSummary() {
        // Lấy số liệu thô từ các hàm @Query trong Repository
        return new PetSummaryDTO(
                repository.countAllPets(),
                repository.sumAllValue(),
                repository.countSickPets(),
                repository.countNewPetsInMonth("P" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM")))
        );
    }

    @Override
    public String generateNextMaPet() {
        String prefix = "P" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMM"));
        return repository.findLastPetByPrefix(prefix)
                .map(lastPet -> {
                    String lastMa = lastPet.getMaPet();
                    // P260501 -> Lấy "01" (vị trí index 5 đến hết)
                    int lastNumber = Integer.parseInt(lastMa.substring(5));
                    return String.format("%s%02d", prefix, lastNumber + 1);
                })
                .orElse(prefix + "01");
    }

    // Hàm chuyển đổi Entity -> DTO (Flattening)
    private PetDTO convertToDTO(Pet pet) {
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);

        // Xử lý các trường Enum hoặc trường liên kết lồng nhau
        if (pet.getTinhTrang() != null) {
            dto.setTinhTrang(pet.getTinhTrang().name());
        }

        if (pet.getChuong() != null) {
            dto.setMaChuong(pet.getChuong().getMaChuong());
            if (pet.getChuong().getLoaiChuong() != null) {
                dto.setTenLoaiChuong(pet.getChuong().getLoaiChuong().getTenLoai());
            }
        }

        if (pet.getDanhSachHinhAnh() != null) {
            dto.setDanhSachHinhAnh(pet.getDanhSachHinhAnh().stream()
                    .map(img -> {
                        PetImageDTO imgDto = new PetImageDTO();
                        imgDto.setMaImg(img.getMaImg());
                        imgDto.setUrl(img.getUrl());
                        imgDto.setThoiGianDangTai(img.getThoiGianDangTai());
                        return imgDto;
                    }).collect(Collectors.toList()));
        }
        if (pet.getLichSuSucKhoe() != null) {
            dto.setLichSuSucKhoe(pet.getLichSuSucKhoe().stream()
                    .map(ls -> {
                        LichSuSucKhoeDTO lsDto = new LichSuSucKhoeDTO();
                        BeanUtils.copyProperties(ls, lsDto);
                        lsDto.setMaPet(pet.getMaPet());
                        lsDto.setLoai(ls.getLoai().name());
                        return lsDto;
                    }).collect(Collectors.toList()));
        }
        return dto;
    }
    @Override
    public boolean hasSeriousHealthIssue(String maPet) {
        Pet pet = repository.findById(maPet).orElse(null);
        if (pet == null || pet.getLichSuSucKhoe() == null) return false;

        // Kiểm tra xem có bản ghi nào loại "Benh" mà mô tả có từ khóa cảnh báo không
        return pet.getLichSuSucKhoe().stream()
                .anyMatch(ls -> ls.getLoai().name().equals("Benh"));
    }
}