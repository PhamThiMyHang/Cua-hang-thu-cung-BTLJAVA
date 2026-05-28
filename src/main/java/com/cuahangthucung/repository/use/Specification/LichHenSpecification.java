package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.lichhen.LichHenSearchRequest;
import com.cuahangthucung.entity.use.entity.LichHen;
import com.cuahangthucung.entity.use.enums.TrangThai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class LichHenSpecification {

    public static Specification<LichHen> getFilter(LichHenSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // 1. Lọc theo Mã Lịch Hẹn cụ thể
            if (request.getMaLich() != null && !request.getMaLich().isBlank()) {
                predicates.add(cb.equal(root.get("maLich"), request.getMaLich().trim()));
            }

            // 2. Lọc theo Mã Khách Hàng (An toàn ép kiểu Integer)
            if (request.getMaKH() != null && !request.getMaKH().isBlank()) {
                try {
                    Integer khId = Integer.parseInt(request.getMaKH().trim());
                    predicates.add(cb.equal(root.get("khachHang").get("maKH"), khId));
                } catch (NumberFormatException ignored) {}
            }

            // 3. Lọc theo Mã Pet (An toàn ép kiểu Integer)
            if (request.getMaPet() != null && !request.getMaPet().isBlank()) {
                try {
                    Integer petId = Integer.parseInt(request.getMaPet().trim());
                    predicates.add(cb.equal(root.get("pet").get("maPet"), petId));
                } catch (NumberFormatException ignored) {}
            }

            // 4. Lọc theo Mã Nhân Viên (An toàn ép kiểu Integer)
            if (request.getMaNV() != null && !request.getMaNV().isBlank()) {
                try {
                    Integer nvId = Integer.parseInt(request.getMaNV().trim());
                    predicates.add(cb.equal(root.get("nhanVien").get("maNV"), nvId));
                } catch (NumberFormatException ignored) {}
            }

            // 5. Lọc theo Mã Dịch Vụ (Kiểu String)
            if (request.getMaDV() != null && !request.getMaDV().isBlank()) {
                predicates.add(cb.equal(root.get("dichVu").get("maDV"), request.getMaDV().trim()));
            }

            // 6. Lọc theo Trạng Thái (Chuyển đổi String sang Enum an toàn)
            if (request.getTrangThai() != null && !request.getTrangThai().isBlank()) {
                try {
                    TrangThai enumTrangThai = TrangThai.valueOf(request.getTrangThai().toUpperCase().trim());
                    predicates.add(cb.equal(root.get("trangThai"), enumTrangThai));
                } catch (IllegalArgumentException ignored) {}
            }

            // 7. Lọc theo Khoảng Thời Gian hẹn
            if (request.getTuNgay() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("thoiGian"), request.getTuNgay()));
            }
            if (request.getDenNgay() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("thoiGian"), request.getDenNgay()));
            }

            // 8. ĐÃ BỔ SUNG: Xử lý Keyword đa năng (Tìm kiếm theo Mã lịch, Tên KH hoặc Tên Pet)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                List<Predicate> orPredicates = new ArrayList<>();

                orPredicates.add(cb.like(cb.lower(root.get("maLich")), likePattern));
                orPredicates.add(cb.like(cb.lower(root.get("khachHang").get("tenKH")), likePattern));
                orPredicates.add(cb.like(cb.lower(root.get("pet").get("tenPet")), likePattern));

                predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}