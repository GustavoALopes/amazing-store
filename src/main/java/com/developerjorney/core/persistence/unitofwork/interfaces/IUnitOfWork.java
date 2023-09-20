package com.developerjorney.core.persistence.unitofwork.interfaces;

import org.springframework.beans.factory.DisposableBean;

public interface IUnitOfWork extends DisposableBean {

    boolean isTransactionOpen();

    boolean begin();

    boolean commit();

    boolean rollback();

    <T> void persist(final T canBeProxy);
}
