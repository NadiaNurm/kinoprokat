package com.example.kinoprokat.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    user, admin;

    @Override
    public String getAuthority() {
        return name();// строковое представление словаря
    }
}
