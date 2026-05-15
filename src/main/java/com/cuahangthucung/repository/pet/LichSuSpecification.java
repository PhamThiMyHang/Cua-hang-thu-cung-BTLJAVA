package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.LichSuSearchRequest;
import com.cuahangthucung.entity.pet.entity.LichSuSucKhoe;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalTime;

public class LichSuSpecification {
    public static Specification<LichSuSucKhoe> getFilter(LichSuSearchRequest request) {
        if (request == null) return null;
        return Specification.allOf(
                GenericSpecification.fieldEquals("pet.maPet", request.getMaPet()),

                // Tìm theo tên Pet (gần đúng - LIKE)
                request.getTenPet() != null && !request.getTenPet().isBlank() ?
                        (root, query, cb) -> cb.like(root.get("pet").get("tenPet"), "%" + request.getTenPet() + "%")
                        : null,

                GenericSpecification.fieldEquals("loai", request.getLoai()),
                GenericSpecification.fieldContains("moTa", request.getTuKhoa()),
                request.getNgay() != null ?
                        GenericSpecification.fieldBetween("ngay",
                                request.getNgay().atStartOfDay(),
                                request.getNgay().atTime(LocalTime.MAX)) : null
        );
    }
}