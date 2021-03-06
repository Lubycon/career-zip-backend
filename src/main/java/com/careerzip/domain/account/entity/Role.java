package com.careerzip.domain.account.entity;

public enum Role {
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN")
    ;

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
