package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.HoSoNhanVienSearchRequest;
import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class HoSoNhanVienSpecification {

    public static Specification<HoSoNhanVien> getFilter(HoSoNhanVienSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldEquals("nhanVien.maNV", request.getMaNV()),
                GenericSpecification.fieldContains("bangCap", request.getKeyword()),
                GenericSpecification.fieldContains("trinhDo", request.getKeyword()),
                GenericSpecification.fieldContains("kinhNghiem", request.getKeyword())
        );
    }
}