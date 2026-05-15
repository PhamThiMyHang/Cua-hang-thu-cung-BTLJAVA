package com.cuahangthucung.repository.user;

import com.cuahangthucung.dto.user.UserSearchRequest;
import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.repository.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> getFilter(UserSearchRequest request) {
        if (request == null) return null;

        return Specification.allOf(
                GenericSpecification.fieldContains("username", request.getUsername()),
                GenericSpecification.fieldEquals("status", request.getStatus()),
                // Tìm theo role (cần join)
                GenericSpecification.fieldIn("roles.roleName", 
                    request.getRoleName() != null ? 
                      java.util.Collections.singletonList(request.getRoleName()) : null)
        );
    }
}