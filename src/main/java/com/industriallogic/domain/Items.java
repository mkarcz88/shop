package com.industriallogic.domain;

import java.math.BigDecimal;

public enum Items {
    SOUP("SOUP", new BigDecimal("0.65")),
    BREAD("BREAD", new BigDecimal("0.80")),
    MILK("MILK", new BigDecimal("1.30")),
    APPLES("APPLES", new BigDecimal("0.10"));

    private String name;
    private BigDecimal price;

    Items(String name, BigDecimal price) {
        this.name = name;
        this.price = new BigDecimal(price.toPlainString());
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
