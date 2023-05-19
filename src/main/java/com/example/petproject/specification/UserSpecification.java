package com.example.petproject.specification;

import com.example.petproject.common.specification.BaseSpecification;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.entity.User;
import org.springframework.data.jpa.domain.Specification;

public abstract class UserSpecification extends BaseSpecification {

    public static Specification<User> buildSpecification(GetUsersRequest request) {
        return containsKeyword(request.getKeyword());
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


}