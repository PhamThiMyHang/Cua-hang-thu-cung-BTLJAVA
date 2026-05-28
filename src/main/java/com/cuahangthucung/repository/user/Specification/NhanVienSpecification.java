package com.cuahangthucung.repository.user.Specification;

import com.cuahangthucung.dto.user.NhanVien.NhanVienSearchRequest;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.repository.GenericSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class NhanVienSpecification {

    public static Specification<NhanVien> getFilter(NhanVienSearchRequest request) {
        if (request == null) return null;

        // 1. Kết hợp các bộ lọc cơ bản bằng GenericSpecification của bạn
        Specification<NhanVien> spec = Specification.allOf(
                GenericSpecification.fieldContains("tenNV", request.getTenNV()),
                GenericSpecification.fieldContains("sdt", request.getSdt()),
                GenericSpecification.fieldContains("gmail", request.getGmail()), // Bổ sung tìm theo Gmail
                GenericSpecification.fieldEquals("chucVu", request.getChucVu())
        );

        // 2. Xử lý bộ lọc tìm kiếm đa năng KEYWORD (Tên NV hoặc SĐT hoặc Địa chỉ hoặc Gmail)
        if (StringUtils.hasText(request.getKeyword())) {
            Specification<NhanVien> keywordSpec = (root, query, cb) -> {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";

                Predicate matchTen = cb.like(cb.lower(root.get("tenNV")), pattern);
                Predicate matchSdt = cb.like(cb.lower(root.get("sdt")), pattern);
                Predicate matchDiaChi = cb.like(cb.lower(root.get("diaChi")), pattern);
                Predicate matchGmail = cb.like(cb.lower(root.get("gmail")), pattern);

                // Gom cụm bằng toán tử OR
                return cb.or(matchTen, matchSdt, matchDiaChi, matchGmail);
            };
            spec = spec.and(keywordSpec);
        }

        // 3. Tạo biến final để tránh lỗi "Variable used in lambda expression should be final"
        final Specification<NhanVien> finalSpecification = spec;

        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            return finalSpecification.toPredicate(root, query, cb);
        };
    }
}