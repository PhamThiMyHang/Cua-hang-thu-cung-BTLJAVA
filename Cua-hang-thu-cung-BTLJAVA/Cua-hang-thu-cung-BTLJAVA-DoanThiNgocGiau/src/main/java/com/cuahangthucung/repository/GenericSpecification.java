package com.cuahangthucung.repository;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification {

    /**
     * Hàm hỗ trợ lấy Path từ fieldName (hỗ trợ cả trường lồng nhau như "chuong.maChuong")
     */
    private static <T> Path<Object> getPath(Root<T> root, String fieldName) {
        if (!fieldName.contains(".")) {
            return root.get(fieldName);
        }
        String[] parts = fieldName.split("\\.");
        Path<Object> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }

    // 1. Tìm kiếm gần đúng (Like) - Dùng cho Live Search (P -> P26 -> P2601)
    public static <T> Specification<T> fieldContains(String fieldName, String value) {
        return (root, query, cb) -> {
            if (value == null || value.isEmpty()) return null;
            // Chuyển về chữ thường để tìm kiếm không phân biệt hoa thường
            return cb.like(cb.lower(getPath(root, fieldName).as(String.class)), "%" + value.toLowerCase() + "%");
        };
    }

    // 2. So sánh bằng (Equal)
    public static <T> Specification<T> fieldEquals(String fieldName, Object value) {
        return (root, query, cb) -> {
            if (value == null) return null;
            return cb.equal(getPath(root, fieldName), value);
        };
    }

    // 3. So sánh khoảng (Between) hoặc Lớn hơn / Nhỏ hơn
    @SuppressWarnings("unchecked")
    public static <T, V extends Comparable<V>> Specification<T> fieldBetween(String fieldName, V min, V max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;

            // Ép kiểu an toàn thông qua Path<?>
            Path<V> path = (Path<V>) (Path<?>) getPath(root, fieldName);

            if (min != null && max != null) return cb.between(path, min, max);
            if (min != null) return cb.greaterThanOrEqualTo(path, min);
            return cb.lessThanOrEqualTo(path, max);
        };
    }

    // 4. Kiểm tra NULL / NOT NULL (Đã trả / Chưa trả)
    public static <T> Specification<T> fieldIsNull(String fieldName, boolean isNull) {
        return (root, query, cb) -> isNull
                ? cb.isNull(getPath(root, fieldName))
                : cb.isNotNull(getPath(root, fieldName));
    }
}