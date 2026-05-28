package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.donhang.DonHangSearchRequest;
import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.enums.TrangThai;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DonHangSpecification {

    public static Specification<DonHang> getFilter(DonHangSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // 1. Lọc theo Mã Đơn Hàng cụ thể (Kiểu String)
            if (request.getMaDH() != null && !request.getMaDH().isBlank()) {
                predicates.add(cb.equal(root.get("maDH"), request.getMaDH()));
            }

            // 2. ĐÃ SỬA: Lọc theo Mã Khách Hàng (Chuyển sang kiểu dữ liệu Integer theo Entity KhachHang)
            if (request.getMaKH() != null && !request.getMaKH().isBlank()) {
                try {
                    Integer maKHInt = Integer.parseInt(request.getMaKH().trim());
                    predicates.add(cb.equal(root.get("khachHang").get("maKH"), maKHInt));
                } catch (NumberFormatException e) {
                    // Nếu chuỗi truyền lên không phải số, bỏ qua điều kiện để tránh sập câu lệnh SQL
                }
            }

            // 3. ĐÃ SỬA: Lọc theo Mã Nhân Viên (Chuyển sang kiểu dữ liệu Integer theo Entity NhanVien)
            if (request.getMaNV() != null && !request.getMaNV().isBlank()) {
                try {
                    Integer maNVInt = Integer.parseInt(request.getMaNV().trim());
                    predicates.add(cb.equal(root.get("nhanVien").get("maNV"), maNVInt));
                } catch (NumberFormatException e) {
                    // Nếu chuỗi truyền lên không phải số, bỏ qua điều kiện
                }
            }

            // 4. Lọc theo Trạng Thái
            if (request.getTrangThai() != null && !request.getTrangThai().isBlank()) {
                try {
                    TrangThai enumTrangThai = TrangThai.valueOf(request.getTrangThai().toUpperCase());
                    predicates.add(cb.equal(root.get("trangThai"), enumTrangThai));
                } catch (IllegalArgumentException e) {
                    // Trạng thái không hợp lệ thì bỏ qua
                }
            }

            // 5. Lọc theo Khoảng Ngày tạo
            if (request.getTuNgay() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ngayTao"), request.getTuNgay()));
            }
            if (request.getDenNgay() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ngayTao"), request.getDenNgay()));
            }

            // 6. Lọc theo Khoảng Tổng Tiền
            if (request.getMinTongTien() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("tongTien"), request.getMinTongTien()));
            }
            if (request.getMaxTongTien() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("tongTien"), request.getMaxTongTien()));
            }

            // 7. ĐÃ SỬA: Keyword tìm theo mã đơn (String) hoặc tên khách hàng (String)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";

                List<Predicate> orPredicates = new ArrayList<>();
                orPredicates.add(cb.like(cb.lower(root.get("maDH")), likePattern));
                orPredicates.add(cb.like(cb.lower(root.get("khachHang").get("tenKH")), likePattern));

                // Nếu từ khóa nhập vào là số, hỗ trợ tìm kiếm chính xác theo mã khách hàng số nguyên luôn
                try {
                    Integer keywordInt = Integer.parseInt(request.getKeyword().trim());
                    orPredicates.add(cb.equal(root.get("khachHang").get("maKH"), keywordInt));
                } catch (NumberFormatException ignored) {}

                predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}