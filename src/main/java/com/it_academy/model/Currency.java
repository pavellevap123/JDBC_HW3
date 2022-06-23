package com.it_academy.model;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    RUB("RUB");

    private final String name;

    private Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
