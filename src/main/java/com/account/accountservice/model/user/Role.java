package com.account.accountservice.model.user;

public enum Role {
    ADMINISTRATOR,
    AUDITOR,
    ACCOUNTANT,
    USER;

    public String withPrefix() {
        return "ROLE_" + this.name();
    }
}
