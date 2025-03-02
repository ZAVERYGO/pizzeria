package com.kozich.projectrepository.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER"),
    ROLE_MANAGER("MANAGER");

    private final String content;

    UserRole(String content) {
        this.content = content;
    }

    @JsonValue
    public String getContactType() {
        return content;
    }

    @JsonCreator
    public static UserRole fromValue(String value) {
        for (UserRole contact : values()) {
            String currentContact = contact.getContactType();
            if (currentContact.equals(value)) {
                return contact;
            }
        }
        throw new IllegalArgumentException("Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

    }
}
