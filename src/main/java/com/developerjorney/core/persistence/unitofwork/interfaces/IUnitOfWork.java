package com.developerjorney.core.persistence.unitofwork.interfaces;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.DisposableBean;

import java.util.function.Supplier;

public interface IUnitOfWork extends DisposableBean {

    boolean isTransactionOpen();

    boolean begin();

    boolean commit();

    boolean rollback();

    <T> void persist(final T canBeProxy);

    <TOut> TOut execute(final Supplier<TOut> action);

    EntityManager getEntityManager();
}
