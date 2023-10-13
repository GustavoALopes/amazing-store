package com.developerjorney.domain.client.entities.valueobjects;

import com.developerjorney.domain.client.entities.specifications.EmailSpecification;
import com.developerjorney.domain.client.entities.specifications.interfaces.IDomainSpecification;
import lombok.Getter;

import java.util.Objects;

@Getter
public class EmailVO {

    private static final IDomainSpecification<String> specification = new EmailSpecification();

    private final String value;

    private final boolean isValid;

    private EmailVO(
        final String value,
        final boolean isValid
    ) {
        this.value = value;
        this.isValid = isValid;
    }

    public static EmailVO create(
        final String email
    ) {
        if(Objects.isNull(email) || email.isEmpty()) {
            return new EmailVO("", false);
        }

        if(!specification.isSatisfiedBy(email)) {
            return new EmailVO(email, false);
        }

        return new EmailVO(email, true);
    }
}
