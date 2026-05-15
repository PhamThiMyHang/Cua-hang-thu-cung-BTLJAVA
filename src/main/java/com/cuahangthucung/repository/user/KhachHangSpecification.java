package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.KhachHangSearchRequest;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class KhachHangSpecification {

    public static Specification<KhachHang> getFilter(KhachHangSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldContains("tenKH", request.getTenKH()),
                GenericSpecification.fieldContains("sdt", request.getSdt()),
                GenericSpecification.fieldEquals("loaiKH", request.getLoaiKH())
        );
    }
}