package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.ChamCongSearchRequest;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class ChamCongSpecification {

    public static Specification<ChamCong> getFilter(ChamCongSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldEquals("nhanVien.maNV", request.getMaNV()),
                GenericSpecification.fieldBetween("ngay", request.getTuNgay(), request.getDenNgay())
        );
    }
}