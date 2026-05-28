package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.giohang.GioHangSearchRequest;
import com.cuahangthucung.entity.use.entity.GioHang;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GioHangSpecification {

    public static Specification<GioHang> getFilter(GioHangSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // Lọc theo mã giỏ hàng
            if (request.getMaGioHang() != null && !request.getMaGioHang().isBlank()) {
                predicates.add(cb.equal(root.get("id").get("maGioHang"), request.getMaGioHang().trim()));
            }

            // Lọc theo mã user
            if (request.getMaUser() != null && !request.getMaUser().isBlank()) {
                predicates.add(cb.equal(root.get("user").get("userID"), Integer.parseInt(request.getMaUser().trim())));
            }

            // Lọc theo mã sản phẩm
            if (request.getMaSP() != null && !request.getMaSP().isBlank()) {
                predicates.add(cb.equal(root.get("id").get("maSP"), request.getMaSP().trim()));
            }

            // Tìm kiếm tổng hợp theo keyword (mã giỏ hoặc tên sản phẩm)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String pattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("id").get("maGioHang")), pattern),
                        cb.like(cb.lower(root.get("sanPham").get("tenSP")), pattern)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}