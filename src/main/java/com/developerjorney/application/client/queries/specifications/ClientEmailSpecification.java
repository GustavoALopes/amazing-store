package com.developerjorney.application.client.queries.specifications;

import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ClientEmailSpecification implements Specification<Client> {

    private final String email;

    public ClientEmailSpecification(final String email) {
        this.email = email;
    }

    @Override
    public Predicate toPredicate(final Root<Client> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(this.email) || email.isEmpty()) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.equal(root.get("email"), this.email);
    }
}
