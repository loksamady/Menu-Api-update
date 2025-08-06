package kh.com.csx.request.subscriptionType;

import jakarta.persistence.criteria.Predicate;
import kh.com.csx.entity.SubscriptionType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionTypeSpecification {
    public static Specification<SubscriptionType> filterBy(SubscriptionTypeFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

