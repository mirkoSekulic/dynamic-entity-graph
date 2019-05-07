package com.dynamic.graph.modules.users.specification;

import com.dynamic.graph.modules.users.domain.Authority_;
import com.dynamic.graph.modules.users.domain.User;
import com.dynamic.graph.modules.users.domain.User_;
import com.dynamic.graph.modules.users.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {
    private static final long serialVersionUID = 1L;

    private UserFilter userFilter;

    private UserSpecification(UserFilter userFilter) {
        this.userFilter = userFilter;
    }

    public static UserSpecification of(UserFilter userFilter) {
        return new UserSpecification(userFilter);
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        String likeFormat = "%%%s%%";

        List<Predicate> predicates = new ArrayList<>();

        if (userFilter.getFirstName() != null) {
            predicates.add(criteriaBuilder.like(root.get(User_.firstName), String.format(likeFormat, userFilter.getFirstName())));
        }

        if (userFilter.getLastName() != null) {
            predicates.add(criteriaBuilder.like(root.get(User_.lastName), String.format(likeFormat, userFilter.getLastName())));
        }

        if (userFilter.getRoles() != null && !userFilter.getRoles().isEmpty()) {
            predicates.add(root.join(User_.authorities).get(Authority_.code).in(userFilter.getRoles()));
        }

        Predicate[] predicateArray = predicates.toArray(new Predicate[0]);

        return criteriaBuilder.and(predicateArray);
    }
}
