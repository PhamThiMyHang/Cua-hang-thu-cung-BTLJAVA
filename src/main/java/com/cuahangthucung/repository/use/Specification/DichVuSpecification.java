package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.dichvu.DichVuSearchRequest;
import com.cuahangthucung.entity.use.entity.DichVu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DichVuSpecification {

    public static Specification<DichVu> getFilter(DichVuSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Tìm kiếm tổng hợp theo Keyword (Tìm cả theo mã hoặc theo tên dịch vụ)
            if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
                String searchPattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                Predicate matchMa = cb.like(cb.lower(root.get("maDV")), searchPattern);
                Predicate matchTen = cb.like(cb.lower(root.get("tenDV")), searchPattern);
                predicates.add(cb.or(matchMa, matchTen));
            }

            // 2. Tìm kiếm chính xác/gần đúng theo tên dịch vụ đơn lẻ nếu có truyền
            if (request.getTenDV() != null && !request.getTenDV().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("tenDV")),
                        "%" + request.getTenDV().trim().toLowerCase() + "%"));
            }

            // 3. Lọc theo khoảng giá tiền
            if (request.getMinGia() != null) {
                predicates.add(cb.ge(root.get("gia"), request.getMinGia()));
            }

            if (request.getMaxGia() != null) {
                predicates.add(cb.le(root.get("gia"), request.getMaxGia()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}