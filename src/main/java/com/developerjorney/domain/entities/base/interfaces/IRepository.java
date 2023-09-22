package com.developerjorney.domain.entities.base.interfaces;

public interface IRepository<T extends IAggregateRoot> {
    boolean persist(final T entity);
}
