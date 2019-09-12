package com.industriallogic.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    private String name;
    private BigDecimal price;

    public Item(Items item) {
        this.name = item.getName();
        this.price = item.getPrice();
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
