//package com.developerjorney.configurations;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//import javax.xml.crypto.Data;
//
//public class FlywayTest {
//
//    @Bean
//    @ConfigurationProperties(value = "spring.datasource")
//    private DataSource dataSource() {
//        return new DriverManagerDataSource();
//    };
//
//    @Bean
//    public void runMigration(final DataSource dataSource) {
//        Flyway
//    }
//}
