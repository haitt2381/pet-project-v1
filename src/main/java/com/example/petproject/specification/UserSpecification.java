package com.example.petproject.specification;

import com.example.petproject.common.specification.BaseSpecification;
import com.example.petproject.common.util.CommonUtils;
import com.example.petproject.constant.Role;
import com.example.petproject.dto.request.GetUsersRequest;
import com.example.petproject.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class UserSpecification extends BaseSpecification {

    public static Specification<User> buildSpecification(GetUsersRequest request) {
        Specification<User> specification = containsKeyword(request.getKeyword());
        specification = specification.and(isDeleted(request.getIsDeleted()));

        if (Boolean.TRUE.equals(request.getIsExcludeCurrentUserLogged())) {
            specification = specification.and(excludeUserLogged());
        }

        if (!CollectionUtils.isEmpty(request.getRole())) {
            specification = specification.and(hasRole(request.getRole()));
        }

        if (Objects.nonNull(request.getIsActive())) {
            specification = specification.and(isActive(request.getIsActive()));
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

    public static Specification<User> hasRole(List<String> roleNames) {
        Set<Role> rolesQuery = roleNames.stream().map(Role::valueOf).collect(Collectors.toSet());
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(root.get("role").in(rolesQuery));
    }

    public static Specification<User> isActive(boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive);
    }

    public static Specification<User> excludeUserLogged() {
        String currentUsernameLogged = CommonUtils.getCurrentUsernameLogged();
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("username"), currentUsernameLogged);
    }

    public static Specification<User> isDeleted(Boolean isDeleted) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), Objects.isNull(isDeleted) ? Boolean.FALSE : isDeleted);
    }

//    public static String buildNativeQuery (GetUsersRequest request, ) {
//
//    }
}
