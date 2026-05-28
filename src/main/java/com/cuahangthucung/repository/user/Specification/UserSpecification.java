package com.cuahangthucung.repository.user.Specification;

import com.cuahangthucung.dto.user.User.UserSearchRequest;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.GenericSpecification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {

    public static Specification<User> getFilter(UserSearchRequest request) {
        if (request == null) return null;

        // 1. Khởi tạo bộ lọc cơ bản ban đầu
        Specification<User> spec = Specification.allOf(
                GenericSpecification.fieldContains("username", request.getUsername()),
                GenericSpecification.fieldContains("gmail", request.getGmail()),
                GenericSpecification.fieldEquals("status", request.getStatus())
        );

        // 2. Bộ lọc RoleName
        if (StringUtils.hasText(request.getRoleName())) {
            Specification<User> roleSpec = (root, query, cb) -> {
                Join<Object, Object> roleJoin = root.join("roles", JoinType.INNER);
                return cb.equal(cb.lower(roleJoin.get("roleName").as(String.class)), request.getRoleName().toLowerCase());
            };
            spec = spec.and(roleSpec);
        }

        // 3. Bộ lọc KEYWORD
        if (StringUtils.hasText(request.getKeyword())) {
            Specification<User> keywordSpec = (root, query, cb) -> {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";

                Join<User, NhanVien> nvJoin = root.join("nhanVien", JoinType.LEFT);
                Join<User, KhachHang> khJoin = root.join("khachHang", JoinType.LEFT);

                Predicate matchUsername = cb.like(cb.lower(root.get("username")), pattern);
                Predicate matchGmail = cb.like(cb.lower(root.get("gmail")), pattern);
                Predicate matchTenNV = cb.like(cb.lower(nvJoin.get("tenNV")), pattern);
                Predicate matchTenKH = cb.like(cb.lower(khJoin.get("tenKH")), pattern);

                return cb.or(matchUsername, matchGmail, matchTenNV, matchTenKH);
            };
            spec = spec.and(keywordSpec);
        }

        // ==================== ĐÃ SỬA Ở ĐÂY ====================
        // Tạo một biến final để hứng giá trị cuối cùng của 'spec'
        final Specification<User> finalSpecification = spec;

        // 4. Đảm bảo loại bỏ bản ghi trùng lặp (Distinct) và trả về Predicate
        return (root, query, cb) -> {
            if (query != null) {
                query.distinct(true);
            }
            // Gọi biến finalSpecification thay vì gọi trực tiếp 'spec'
            return finalSpecification.toPredicate(root, query, cb);
        };
    }
}