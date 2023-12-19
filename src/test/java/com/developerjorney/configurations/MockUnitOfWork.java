package com.developerjorney.configurations;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Transaction;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockUnitOfWork {

    @Bean
    public EntityManager entityManager(
            final EntityManagerFactory factory
    ) {
        final var mock = Mockito.mock(EntityManager.class);

        Mockito.when(mock.getEntityManagerFactory()).thenReturn(factory);

        return mock;
    };

    @Bean
    public EntityManagerFactory factory() {
        final var mock = Mockito.mock(EntityManagerFactory.class);
        final var managerMock = Mockito.mock(EntityManager.class);
        final var transactionMock = Mockito.mock(Transaction.class);

        Mockito.when(managerMock.getTransaction()).thenReturn(transactionMock);
        Mockito.when(transactionMock.isActive()).thenReturn(false, true, false, true, false, true, false, true, false, true);

        Mockito.when(mock.createEntityManager()).thenReturn(managerMock);

        return mock;
    };

}
