package com.developerjorney.infra.repositories.models;

import com.developerjorney.domain.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductJpaModel extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
}
