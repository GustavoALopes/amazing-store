package com.developerjorney.application.enums;

import lombok.Getter;

@Getter
public enum APIVersion {
    v1("/api/v1");

    private final String value;

    APIVersion(final String value) {
        this.value = value;
    }
}
