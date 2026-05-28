package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapSearchRequest;
import com.cuahangthucung.entity.use.entity.NhaCungCap;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class NhaCungCapSpecification {

    public static Specification<NhaCungCap> getFilter(NhaCungCapSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // 1. Lọc theo mã nhà cung cấp chính xác
            if (request.getMaNCC() != null && !request.getMaNCC().isBlank()) {
                predicates.add(cb.equal(root.get("maNCC"), request.getMaNCC().trim()));
            }

            // 2. Lọc theo tên nhà cung cấp gần đúng
            if (request.getTenNCC() != null && !request.getTenNCC().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("tenNCC")),
                        "%" + request.getTenNCC().trim().toLowerCase() + "%"));
            }

            // 3. Lọc theo số điện thoại gần đúng
            if (request.getSdt() != null && !request.getSdt().isBlank()) {
                predicates.add(cb.like(root.get("sdt"), "%" + request.getSdt().trim() + "%"));
            }

            // 4. Xử lý bộ lọc Keyword tổng hợp (Tìm theo tên hoặc sdt)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("tenNCC")), likePattern),
                        cb.like(root.get("sdt"), likePattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}