package com.developerjorney.domain.entities.product;

import com.developerjorney.domain.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
public class Product extends BaseEntity<UUID> {

    private String code;

    private String description;
}
