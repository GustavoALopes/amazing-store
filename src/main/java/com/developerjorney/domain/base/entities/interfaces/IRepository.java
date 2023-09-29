package com.developerjorney.domain.base.entities.interfaces;

public interface IRepository<T extends IAggregateRoot> {
    boolean persist(final T entity);
}
