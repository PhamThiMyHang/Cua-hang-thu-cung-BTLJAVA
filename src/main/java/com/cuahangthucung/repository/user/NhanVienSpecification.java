package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.NhanVienSearchRequest;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class NhanVienSpecification {

    public static Specification<NhanVien> getFilter(NhanVienSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldContains("tenNV", request.getTenNV()),
                GenericSpecification.fieldContains("sdt", request.getSdt()),
                GenericSpecification.fieldEquals("chucVu", request.getChucVu())
        );
    }
}