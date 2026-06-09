package com.cuahangthucung.repository.use.Specification;

import com.cuahangthucung.dto.use.sanpham.SanPhamSearchRequest;
import com.cuahangthucung.entity.use.entity.SanPham;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SanPhamSpecification {

    public static Specification<SanPham> getFilter(SanPhamSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            // 1. Lọc theo mã sản phẩm chính xác
            if (request.getMaSP() != null && !request.getMaSP().isBlank()) {
                predicates.add(cb.equal(root.get("maSP"), request.getMaSP()));
            }

            // 2. Lọc theo tên sản phẩm (gần đúng)
            if (request.getTenSP() != null && !request.getTenSP().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("tenSP")),
                        "%" + request.getTenSP().toLowerCase() + "%"));
            }

            // 3. Lọc theo mã nhà cung cấp (Liên kết thực thể qua thuộc tính nhaCungCap.maNCC)
            if (request.getMaNCC() != null && !request.getMaNCC().isBlank()) {
                predicates.add(cb.equal(root.get("nhaCungCap").get("maNCC"), request.getMaNCC()));
            }

            // 4. Lọc theo vị trí kho (gần đúng)
            if (request.getViTri() != null &&
                    !request.getViTri().isBlank()) {

                String key = "%" + request.getViTri().toLowerCase() + "%";

                predicates.add(
                        cb.or(
                                cb.like(
                                        cb.lower(
                                                root.get("viTriSanPham")
                                                        .get("maViTri")
                                        ),
                                        key
                                ),
                                cb.like(
                                        cb.lower(
                                                root.get("viTriSanPham")
                                                        .get("viTri")
                                        ),
                                        key
                                )
                        )
                );
            }

            // 5. Lọc theo khoảng Giá (minGia -> maxGia)
            if (request.getMinGia() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("gia"), request.getMinGia()));
            }
            if (request.getMaxGia() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("gia"), request.getMaxGia()));
            }

            // 6. Lọc theo khoảng Số lượng trong kho
            if (request.getMinSoLuong() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("soLuong"), request.getMinSoLuong()));
            }
            if (request.getMaxSoLuong() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("soLuong"), request.getMaxSoLuong()));
            }

            // 7. Lọc theo trạng thái Còn hàng / Hết hàng
            if (request.getConHang() != null) {
                if (request.getConHang()) {
                    predicates.add(cb.greaterThan(root.get("soLuong"), 0)); // Số lượng > 0
                } else {
                    predicates.add(cb.equal(root.get("soLuong"), 0));       // Số lượng == 0
                }
            }

            // 8. Lọc theo khoảng Hạn sử dụng (Hạn dùng từ ngày -> Đến ngày)
            if (request.getHanSuDungTu() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("hanSuDung"), request.getHanSuDungTu()));
            }
            if (request.getHanSuDungDen() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("hanSuDung"), request.getHanSuDungDen()));
            }

            // 9. Tìm kiếm nhanh theo Từ khóa (Keyword: tìm theo mã sản phẩm hoặc tên sản phẩm)
            if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
                String likePattern = "%" + request.getKeyword().trim().toLowerCase() + "%";
                Predicate keywordMatch = cb.or(
                        cb.like(cb.lower(root.get("maSP")), likePattern),
                        cb.like(cb.lower(root.get("tenSP")), likePattern)
                );
                predicates.add(keywordMatch);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}