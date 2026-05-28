package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.entity.use.entity.ChiTietDonHang;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangSpecification {

    public static Specification<ChiTietDonHang> getFilter(String maDH, String maSP) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Thay vì get("id").get(...) -> đi trực tiếp qua các thuộc tính liên kết ManyToOne
            if (maDH != null && !maDH.isBlank()) {
                predicates.add(cb.equal(root.get("donHang").get("maDH"), maDH));
            }

            if (maSP != null && !maSP.isBlank()) {
                predicates.add(cb.equal(root.get("sanPham").get("maSP"), maSP));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}