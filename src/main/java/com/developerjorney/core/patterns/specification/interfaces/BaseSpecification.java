package com.developerjorney.core.patterns.specification.interfaces;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseSpecification<T> implements ISpecification<T> {


    public abstract boolean isSatisfiedBy(final T value);

    @Override
    public AndSpecification<T> and(final ISpecification<T> other) {
        return new AndSpecification<T>(this, other);
    }

    @Override
    public AndNotSpecification<T> andNot(final ISpecification<T> other) {
        return new AndNotSpecification<T>(this, other);
    }

    @Override
    public OrSpecification<T> or(final ISpecification<T> other) {
        return new OrSpecification<T>(this, other);
    }

    @Override
    public ISpecification<T> orNot(final ISpecification<T> other) {
        return new OrNotSpecification(this, other);
    }

    private class AndSpecification<T> extends BaseSpecification<T> {
        private final ISpecification<T> left;

        private final ISpecification<T> right;

        public AndSpecification(
                 final ISpecification<T> left,
                 final ISpecification<T> right
        ) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isSatisfiedBy(final T value) {
            return this.left.isSatisfiedBy(value) && this.right.isSatisfiedBy(value);
        }
    }

    private class OrSpecification<T> extends BaseSpecification<T> {

        private final ISpecification<T> left;

        private final ISpecification<T> right;

        public OrSpecification(
                 final ISpecification<T> left,
                 final ISpecification<T> right
        ) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isSatisfiedBy(final T value) {
            return this.left.isSatisfiedBy(value) || this.right.isSatisfiedBy(value);
        }
    }

    private class AndNotSpecification<T> extends BaseSpecification<T> {

        private final ISpecification<T> left;

        private final ISpecification<T> right;

        public AndNotSpecification(
                final ISpecification<T> left,
                final ISpecification<T> right
        ) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isSatisfiedBy(final T value) {
            return this.left.isSatisfiedBy(value) &&
                    this.right.isSatisfiedBy(value) != true;
        }
    }

    private class OrNotSpecification<T> extends BaseSpecification<T> {

        private final ISpecification<T> left;

        private final ISpecification<T> right;

        public OrNotSpecification(
                final ISpecification<T> left,
                final ISpecification<T> right
        ) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean isSatisfiedBy(final T value) {
            return this.left.isSatisfiedBy(value) ||
                    this.right.isSatisfiedBy(value) != true;
        }
    }
}