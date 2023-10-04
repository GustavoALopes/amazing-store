package com.developerjorney.application.client.queries.specifications;

import com.developerjorney.application.client.dtos.input.RangeDateInput;
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

    public ClientRangeBirthdateSpecification(final RangeDateInput dates) {
        if(this.checkIfNull(dates.initDate()) || this.checkIfNull(dates.finalDate())) {
            this.initDate = null;
            this.finalDate = null;
        } else {
            this.initDate = LocalDate.parse(dates.initDate());
            this.finalDate = LocalDate.parse(dates.finalDate());
        }
    }

    @Override
    public Predicate toPredicate(final Root<Client> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
        if(Objects.isNull(this.initDate) || Objects.isNull(this.finalDate)) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }

        return criteriaBuilder.between(root.get("birthdate"), this.initDate, this.finalDate);
    }

    private boolean checkIfNull(final String value) {
        return Objects.isNull(value) || value.isEmpty();
    }
}
