package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.PetImageSearchRequest;
import com.cuahangthucung.entity.pet.entity.PetImage;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;

public class PetImageSpecification {

    public static Specification<PetImage> getFilter(PetImageSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                // 1. Tìm thông tin về một bản ghi có maImg cụ thể
                GenericSpecification.fieldEquals("maImg", request.getMaImg()),

                // 2. Tìm tất cả bản ghi của một pet theo mã
                GenericSpecification.fieldEquals("pet.maPet", request.getMaPet()),

                // Tìm theo tên Pet (Join sang bảng Pet)
                request.getTenPet() != null && !request.getTenPet().isBlank() ?
                        (root, query, cb) -> cb.like(root.get("pet").get("tenPet"), "%" + request.getTenPet() + "%")
                        : null,

                // 3, 5, 6. Tìm bản ghi trong một ngày cụ thể (Xử lý từ 00:00:00 đến 23:59:59)
                request.getNgayCuThe() != null ?
                        GenericSpecification.fieldBetween(
                                "thoiGianDangTai",
                                request.getNgayCuThe().atStartOfDay(),
                                request.getNgayCuThe().atTime(LocalTime.MAX)
                        ) : null,

                // (Tùy chọn) Lọc theo khoảng thời gian tuNgay - denNgay nếu request có dùng
                GenericSpecification.fieldBetween("thoiGianDangTai", request.getTuNgay(), request.getDenNgay())
        );
    }
}