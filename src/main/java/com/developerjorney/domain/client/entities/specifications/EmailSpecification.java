package com.developerjorney.domain.client.entities.specifications;

import com.developerjorney.core.patterns.specification.interfaces.BaseSpecification;
import com.developerjorney.domain.client.entities.specifications.interfaces.IDomainSpecification;

import java.util.Objects;
import java.util.regex.Pattern;

public class EmailSpecification extends BaseSpecification<String> implements IDomainSpecification<String> {

    private final Pattern pattern;

    public EmailSpecification() {
        this.pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");
    }

    @Override
    public boolean isSatisfiedBy(final String value) {
        if(Objects.isNull(value)) return false;
        return this.pattern.matcher(value).matches();
    }
}
