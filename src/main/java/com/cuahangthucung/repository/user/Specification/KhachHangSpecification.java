package com.cuahangthucung.repository.user.Specification;

import com.cuahangthucung.dto.user.khachhang.KhachHangSearchRequest;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.repository.GenericSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class KhachHangSpecification {

    public static Specification<KhachHang> getFilter(KhachHangSearchRequest request) {
        if (request == null) return null;

        // 1. Lọc các điều kiện cơ bản bằng GenericSpecification
        Specification<KhachHang> spec = Specification.allOf(
                GenericSpecification.fieldContains("tenKH", request.getTenKH()),
                GenericSpecification.fieldContains("sdt", request.getSdt()),
                GenericSpecification.fieldContains("gmail", request.getGmail()), // Bổ sung lọc theo Gmail
                GenericSpecification.fieldEquals("loaiKH", request.getLoaiKH())
        );

        // 2. Lọc theo điểm tích lũy tối thiểu (diemTichLuy >= diemTichLuyMin)
        if (request.getDiemTichLuyMin() != null) {
            Specification<KhachHang> diemSpec = (root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("diemTichLuy"), request.getDiemTichLuyMin());
            spec = spec.and(diemSpec);
        }

        // 3. Bộ lọc KEYWORD đa năng (Tìm theo Tên HOẶC SĐT HOẶC Địa chỉ HOẶC Gmail)
        if (StringUtils.hasText(request.getKeyword())) {
            Specification<KhachHang> keywordSpec = (root, query, cb) -> {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";

                Predicate matchTen = cb.like(cb.lower(root.get("tenKH")), pattern);
                Predicate matchSdt = cb.like(cb.lower(root.get("sdt")), pattern);
                Predicate matchDiaChi = cb.like(cb.lower(root.get("diaChi")), pattern);
                Predicate matchGmail = cb.like(cb.lower(root.get("gmail")), pattern);

                return cb.or(matchTen, matchSdt, matchDiaChi, matchGmail);
            };
            spec = spec.and(keywordSpec);
        }

        // 4. Bọc biến tránh lỗi biên dịch Lambda và loại bỏ trùng lặp record
        final Specification<KhachHang> finalSpecification = spec;

        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            return finalSpecification.toPredicate(root, query, cb);
        };
    }
}