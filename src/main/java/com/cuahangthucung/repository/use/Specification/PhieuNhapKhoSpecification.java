package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.phieunhapkho.PhieuNhapKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuNhapKho;
import com.cuahangthucung.entity.use.entity.SanPham;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PhieuNhapKhoSpecification {

    public static Specification<PhieuNhapKho> getFilter(PhieuNhapKhoSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) return cb.and(predicates.toArray(new Predicate[0]));

            Join<PhieuNhapKho, SanPham> joinSanPham = root.join("sanPham", JoinType.LEFT);

            if (request.getMaPhieu() != null && !request.getMaPhieu().isBlank()) {
                // Gọi trực tiếp thuộc tính phẳng, không đi qua .get("id")
                predicates.add(cb.equal(root.get("maPhieu"), request.getMaPhieu().trim()));
            }

            if (request.getMaSP() != null && !request.getMaSP().isBlank()) {
                predicates.add(cb.equal(joinSanPham.get("maSP"), request.getMaSP().trim()));
            }

            if (request.getTenSP() != null && !request.getTenSP().isBlank()) {
                predicates.add(cb.like(cb.lower(joinSanPham.get("tenSP")),
                        "%" + request.getTenSP().trim().toLowerCase() + "%"));
            }

            if (request.getTuNgay() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ngayNhap"), request.getTuNgay()));
            }

            if (request.getDenNgay() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ngayNhap"), request.getDenNgay()));
            }

            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String pattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("maPhieu")), pattern),
                        cb.like(cb.lower(joinSanPham.get("tenSP")), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}