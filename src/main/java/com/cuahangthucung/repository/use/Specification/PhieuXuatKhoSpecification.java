package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.phieuxuatkho.PhieuXuatKhoSearchRequest;
import com.cuahangthucung.entity.use.entity.PhieuXuatKho;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PhieuXuatKhoSpecification {

    public static Specification<PhieuXuatKho> getFilter(PhieuXuatKhoSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (request.getMaPhieu() != null && !request.getMaPhieu().isBlank()) {
                predicates.add(cb.equal(root.get("maPhieu"), request.getMaPhieu().trim()));
            }

            // Lọc theo mã sản phẩm thông qua join thực thể sanPham
            if (request.getMaSP() != null && !request.getMaSP().isBlank()) {
                predicates.add(cb.equal(root.get("sanPham").get("maSP"), request.getMaSP().trim()));
            }

            // Lọc theo mã nhân viên thông qua join thực thể nhanVien
            if (request.getMaNV() != null && !request.getMaNV().isBlank()) {
                predicates.add(cb.equal(root.get("nhanVien").get("maNV"), request.getMaNV().trim()));
            }

            if (request.getNoiNhan() != null && !request.getNoiNhan().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("noiNhan")), "%" + request.getNoiNhan().toLowerCase() + "%"));
            }

            if (request.getTuNgay() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ngayXuat"), request.getTuNgay()));
            }

            if (request.getDenNgay() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ngayXuat"), request.getDenNgay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}