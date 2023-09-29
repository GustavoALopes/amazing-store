package com.developerjorney;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
@EntityScan(value = {"com.developerjorney.domain.product.entities"})
@EnableJpaRepositories(value = {"com.developerjorney.infra.repositories.*"})
public abstract class BasePostgreSQLContainer {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:16.0-alpine");

    static {

    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(final ConfigurableApplicationContext context) {
            TestPropertyValues.of(Map.of(
                "spring.datasource.url", container.getJdbcUrl(),
                "spring.datasource.username", container.getUsername(),
                "spring.datasource.password", container.getPassword(),
                "spring.flyway.url", container.getJdbcUrl(),
                "spring.flyway.user", container.getUsername(),
                "spring.flyway.password", container.getPassword()
            ));
        }
    }


}
