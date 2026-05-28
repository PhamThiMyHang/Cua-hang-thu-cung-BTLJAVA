package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiSearchRequest;
import com.cuahangthucung.entity.use.entity.KhuyenMai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiSpecification {

    public static Specification<KhuyenMai> getFilter(KhuyenMaiSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            LocalDate homNay = LocalDate.now();

            // 1. Tìm kiếm theo cụm từ khóa (Keyword) -> Tìm gần đúng theo mã hoặc tên
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("maKM")), likePattern),
                        cb.like(cb.lower(root.get("tenKM")), likePattern)
                ));
            }

            // 2. Tìm theo Mã KM chính xác
            if (request.getMaKM() != null && !request.getMaKM().isBlank()) {
                predicates.add(cb.equal(root.get("maKM"), request.getMaKM().trim()));
            }

            // 3. Tìm theo Tên KM gần đúng
            if (request.getTenKM() != null && !request.getTenKM().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("tenKM")),
                        "%" + request.getTenKM().trim().toLowerCase() + "%"));
            }

            // 4. Lọc theo trạng thái Còn hiệu lực / Hết hiệu lực
            if (request.getConHieuLuc() != null) {
                if (request.getConHieuLuc()) {
                    // Còn hiệu lực: ngayBD <= homNay <= ngayKT
                    predicates.add(cb.lessThanOrEqualTo(root.get("ngayBD"), homNay));
                    predicates.add(cb.greaterThanOrEqualTo(root.get("ngayKT"), homNay));
                } else {
                    // Hết hiệu lực: homNay < ngayBD HOẶC homNay > ngayKT
                    predicates.add(cb.or(
                            cb.lessThan(root.get("ngayKT"), homNay),
                            cb.greaterThan(root.get("ngayBD"), homNay)
                    ));
                }
            }

            // 5. Lọc theo khoảng ngày (Ví dụ: Chương trình diễn ra trong khoảng từ ngày... đến ngày...)
            if (request.getTuNgay() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ngayBD"), request.getTuNgay()));
            }

            if (request.getDenNgay() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ngayKT"), request.getDenNgay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}