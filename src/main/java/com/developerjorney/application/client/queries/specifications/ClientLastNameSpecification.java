package com.developerjorney.application.client.queries.specifications;

import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ClientLastNameSpecification implements Specification<Client> {

    private final String lastName;

    public ClientLastNameSpecification(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Predicate toPredicate(final Root<Client> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(this.lastName) || this.lastName.isEmpty()) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("lastName")),
                "%" + this.lastName.toLowerCase() + "%"
        );
    }
}
