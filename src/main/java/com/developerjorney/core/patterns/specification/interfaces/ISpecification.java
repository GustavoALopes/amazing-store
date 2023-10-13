package com.developerjorney.core.patterns.specification.interfaces;

public interface ISpecification<T> {

    boolean isSatisfiedBy(final T value);

    ISpecification<T> and(final ISpecification<T> other);

    ISpecification<T> andNot(final ISpecification<T> other);

    ISpecification<T> or(final ISpecification<T> other);

    ISpecification<T> orNot(final ISpecification<T> other);
}
