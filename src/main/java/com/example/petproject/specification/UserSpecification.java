package com.example.petproject.specification;

import com.example.petproject.common.specification.BaseSpecification;
import com.example.petproject.constant.Role;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public abstract class UserSpecification extends BaseSpecification {

    public static Specification<User> buildSpecification(GetUsersRequest request) {
        Specification<User> specification = containsKeyword(request.getKeyword());
        if (StringUtils.hasText(request.getRole())) {
            specification = specification.and(hasRole(request.getRole()));
        }
        return specification;
    }

    public static Specification<User> containsKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String searchText = getSearchText(keyword);

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.concat(criteriaBuilder.lower(root.get("firstName")), criteriaBuilder.lower(root.get("lastName"))), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), searchText)
            );
        };
    }

    public static Specification<User> hasRole(String roleName) {
        Role role = Role.valueOf(roleName);
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("role"), role)
        );
    }


}
