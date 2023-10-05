package com.developerjorney.application.client.queries.specifications;

import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class ClientNameSpecification implements Specification<Client> {

    private final String name;

    public ClientNameSpecification(final String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(final Root<Client> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(this.name) || this.name.isEmpty()) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.equal(root.get("name"), this.name);
    }
}
