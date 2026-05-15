package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.KPIThuongPhatSearchRequest;
import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class KPIThuongPhatSpecification {

    public static Specification<KPIThuongPhat> getFilter(KPIThuongPhatSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldEquals("nhanVien.maNV", request.getMaNV()),
                GenericSpecification.fieldEquals("thang", request.getThang())
        );
    }
}