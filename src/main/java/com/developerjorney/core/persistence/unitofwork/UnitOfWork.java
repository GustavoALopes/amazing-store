package com.developerjorney.core.persistence.unitofwork;

import com.developerjorney.core.persistence.unitofwork.interfaces.IUnitOfWork;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UnitOfWork implements IUnitOfWork {

    private final EntityManager context;

    public UnitOfWork(final EntityManager manager) {
        this.context = manager.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public boolean begin() {
        if(this.isTransactionOpen()) throw new RuntimeException("Transaction already opened!");
        this.context.getTransaction().begin();
        return true;
    }

    @Override
    public boolean commit() {
        if(!this.isTransactionOpen()) throw new RuntimeException("Transaction is not opened");
        this.context.getTransaction().commit();
        this.context.clear();
        return true;
    }

    @Override
    public boolean rollback() {
        if(!this.isTransactionOpen()) throw new RuntimeException("Transaction is not opened");
        this.context.getTransaction().rollback();
        this.context.clear();
        return true;
    }

    @Override
    public <T> void persist(final T canBeProxy) {
        final var entity = this.handleWithProxy(canBeProxy);
        if(this.isNew(entity)) {
            this.context.persist(entity);
        } else {
            this.context.merge(entity);
        }
    }

    private <T> boolean isNew(final T entity) {
        final var entityInfo = JpaEntityInformationSupport.<T>getEntityInformation(
                (Class<T>) entity.getClass(),
                this.context
        );
        return entityInfo.isNew(entity);
    }

    private <T> T handleWithProxy(final T canBeProxy) {
        if(canBeProxy instanceof HibernateProxy){
            return (T)Hibernate.unproxy(canBeProxy);
        }
        return canBeProxy;
    }

    @Override
    public boolean isTransactionOpen() {
        return this.context.getTransaction().isActive();
    }

    @Override
    public void destroy() throws Exception {
        if(isTransactionOpen()) {
            this.rollback();
        }
    }
}
