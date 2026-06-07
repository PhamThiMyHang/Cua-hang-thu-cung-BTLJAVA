package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.yeuthich.YeuThichSearchRequest;
import com.cuahangthucung.entity.use.entity.YeuThich;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class YeuThichSpecification {

    public static Specification<YeuThich> getFilter(YeuThichSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getMaUser() != null && !request.getMaUser().isBlank()) {
                predicates.add(cb.equal(root.get("id").get("maUser"), Integer.parseInt(request.getMaUser().trim())));
            }

            if (request.getMaSP() != null && !request.getMaSP().isBlank()) {
                predicates.add(cb.equal(root.get("id").get("maSP"), request.getMaSP().trim()));
            }


            // Tìm kiếm theo keyword (tên sản phẩm)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String pattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("sanPham").get("tenSP")), pattern));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}