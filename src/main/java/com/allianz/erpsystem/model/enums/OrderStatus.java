package com.allianz.erpsystem.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    ON_TRANSFER("ON_TRANSFER"),
    DENIED("DENIED");
    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

}
