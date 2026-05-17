package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.LichTrucSearchRequest;
import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class LichTrucSpecification {

    public static Specification<LichTruc> getFilter(LichTrucSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldEquals("nhanVien.maNV", request.getMaNV()),
                GenericSpecification.fieldEquals("caLamViec", request.getCaLamViec()),
                // BỔ SUNG: Khớp điều kiện lọc theo một ngày cụ thể nếu có truyền lên
                GenericSpecification.fieldEquals("ngay", request.getNgay()),
                GenericSpecification.fieldBetween("ngay", request.getTuNgay(), request.getDenNgay())
        );
    }
}