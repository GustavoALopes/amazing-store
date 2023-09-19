package com.developerjorney.domain.entities.base;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<T> {

    protected T id;


}
