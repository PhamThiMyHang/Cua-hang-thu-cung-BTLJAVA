package com.cuahangthucung.service.pet;


import com.cuahangthucung.dto.pet.*;
import com.cuahangthucung.entity.pet.entity.Chuong;

import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import com.cuahangthucung.repository.pet.ChuongRepository;
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

    private final ChuongRepository chuongRepository;

    public PetServiceImpl(PetRepository repository, ChuongRepository chuongRepository) {
        super(repository);
        this.chuongRepository = chuongRepository;
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
        Pet pet;

        // 1. Kiểm tra là thêm mới hay cập nhật
        if (request.getMaPet() != null && !request.getMaPet().trim().isEmpty()) {
            pet = repository.findById(request.getMaPet())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thú cưng mã: " + request.getMaPet()));
            // Nếu thay đổi chuồng, hãy giải phóng chuồng cũ
            if (pet.getChuong() != null && !pet.getChuong().getMaChuong().equals(request.getMaChuong())) {
                Chuong chuongCu = pet.getChuong();
                chuongCu.setTrangThai(TrangThaiChuong.TRONG);
                chuongRepository.save(chuongCu);
            }
        } else {
            pet = new Pet();
            String newId = generateNextMaPet(); // Sinh mã (VD: P260501)
            pet.setMaPet(newId);               // Gán trực tiếp vào Entity để tránh lỗi 'ma_pet' null
        }

        // 2. Copy dữ liệu cơ bản (Tên, Giống, Tuổi, Gia, CanNang, TinhTrang, NgayTra)
        BeanUtils.copyProperties(request, pet, "maPet", "chuong", "maKH", "maNV"); // Không copy đè mã pet

        // 3. Mapping Quan hệ: Chuồng (Chuong)
        if (request.getMaChuong() != null) {
            Chuong chuong = chuongRepository.findById(request.getMaChuong())
                    .orElseThrow(() -> new RuntimeException("Chuồng không tồn tại: " + request.getMaChuong()));

            // Logic bổ sung: Nếu chuồng đang sửa chữa thì không cho gửi pet
            if (chuong.getTrangThai() == com.cuahangthucung.entity.pet.enums.TrangThaiChuong.SUA_CHUA) {
                throw new RuntimeException("Chuồng này đang sửa chữa, không thể tiếp nhận thú cưng!");
            }

            pet.setChuong(chuong);
            chuong.setTrangThai(TrangThaiChuong.KIN);
            chuongRepository.save(chuong);
        }

        // 4. Mapping Quan hệ: Khách hàng & Nhân viên
        // Lưu ý: Vì trong Entity bạn đang để MaKH và MaNV là kiểu Integer (không phải Object)
        // nên chỉ cần set trực tiếp giá trị ID từ Request.
        if (request.getMaKH() != null) {
            pet.setMaKH(request.getMaKH());
        }
        if (request.getMaNV() != null) {
            pet.setMaNV(request.getMaNV());
        }

        // 5. Lưu vào Database
        Pet saved = repository.save(pet);

        // 6. Trả về DTO đã được làm phẳng
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
    public PetDTO convertToDTO(Pet pet) {
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

    /*Them chức năng*/
    @Override
    public List<PetDTO> findAllDTO() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    @Transactional
    public void delete(String maPet) {
        // 1. Tìm thông tin pet trước khi xóa
        Pet pet = repository.findById(maPet)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thú cưng mã: " + maPet));

        // 2. Giải phóng chuồng (nếu pet đang ở trong chuồng)
        if (pet.getChuong() != null) {
            Chuong chuong = pet.getChuong();
            chuong.setTrangThai(TrangThaiChuong.TRONG); //
            chuongRepository.save(chuong);
        }

        // 3. Thực hiện xóa
        // Lưu ý: Các bảng liên quan như PET_IMAGE hoặc LICHSU_SUC_KHOE
        // sẽ tự động xóa nếu bạn đã cấu hình cascade = CascadeType.ALL.
        repository.delete(pet);

    }
}