package com.cuahangthucung.repository.user.Specification;

import com.cuahangthucung.dto.user.LichSuDangNhap.LichSuDangNhapSearchRequest;
import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class LichSuDangNhapSpecification {

    public static Specification<LichSuDangNhap> getFilter(LichSuDangNhapSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldEquals("user.userID", request.getUserID()),
                GenericSpecification.fieldEquals("user.username", request.getUsername()),
                GenericSpecification.fieldEquals("trangThai", request.getTrangThai()),
                GenericSpecification.fieldBetween("thoiGian", request.getTuNgay(), request.getDenNgay())
        );
    }
}