package com.developerjorney.infra.repositories.models;

import com.developerjorney.domain.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductJpaModel extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
}
