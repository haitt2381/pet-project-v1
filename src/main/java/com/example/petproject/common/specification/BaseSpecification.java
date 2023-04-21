package com.example.petproject.common.specification;

import com.google.common.base.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class BaseSpecification {
    public static final String DEFAULT_SEARCH = "%%";

    public static String getSearchText(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return DEFAULT_SEARCH;
        }
        if (text.contains("_") || text.contains("%")) {
            text = text.replace("_", "\\_");
            text = text.replace("%", "\\%");
        }

        text = "%" + text.toLowerCase() + "%";

        return text.toLowerCase();
    }

    public static Predicate buildMultipleFieldsLikeQueries(CriteriaBuilder builder, List<Expression> expressions, Set<String> texts) {
        return builder.or(texts.stream()
                .map(text -> builder.or(expressions.stream().map(e -> buildLikeQuery(builder, e, text)).toArray(size -> new Predicate[size])))
                .toArray(size -> new Predicate[size]));
    }

    public static Predicate buildLikeQuery(CriteriaBuilder builder, Expression e, String text) {
        return builder.like(builder.lower(e), getSearchText(text));
    }

    public static <T> Specification<T> idIn(Collection<UUID> ids) {
        Set<UUID> idSet = new HashSet<>(ids);
        if (CollectionUtils.isEmpty(idSet)) {
            return null;
        }
        return (root, query, builder) -> root.get("id").in(idSet);
    }
}