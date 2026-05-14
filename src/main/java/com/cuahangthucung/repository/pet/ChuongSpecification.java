package com.cuahangthucung.repository.pet;

import com.cuahangthucung.dto.pet.ChuongSearchRequest;
import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class ChuongSpecification {

    public static Specification<Chuong> getFilter(ChuongSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                // 1. Lọc theo mã chuồng (Like)
                GenericSpecification.fieldContains("maChuong", request.getMaChuong()),

                // 2. Lọc theo trạng thái (Equal Enum)
                GenericSpecification.fieldEquals("trangThai", request.getTrangThai()),

                // 3. Lọc theo mã loại chuồng (Nested Path: loaiChuong.maLoaiChuong)
                GenericSpecification.fieldEquals("loaiChuong.maLoaiChuong", request.getMaLoaiChuong())
        );
    }
}