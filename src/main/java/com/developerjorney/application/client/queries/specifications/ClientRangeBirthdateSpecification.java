package com.developerjorney.application.client.queries.specifications;

import com.developerjorney.domain.client.entities.Client;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Objects;

public class ClientRangeBirthdateSpecification implements Specification<Client> {

    private final LocalDate initDate;

    private final LocalDate finalDate;

    public ClientRangeBirthdateSpecification(
            final LocalDate initDate,
            final LocalDate finalDate
    ) {
        if(Objects.isNull(initDate) || Objects.isNull(finalDate)) {
            this.initDate = null;
            this.finalDate = null;
        } else {
            this.initDate = initDate;
            this.finalDate = finalDate;
        }
    }

    @Override
    public Predicate toPredicate(final Root<Client> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(this.initDate) || Objects.isNull(this.finalDate)) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.between(root.get("birthdate"), this.initDate, this.finalDate);
    }
}
