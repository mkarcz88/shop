package com.industriallogic.service;

import com.industriallogic.domain.Item;
import com.industriallogic.domain.Items;
import com.industriallogic.domain.promotion.ApplePromotion;
import com.industriallogic.domain.promotion.BreadPromotion;
import com.industriallogic.domain.promotion.Promotion;
import com.industriallogic.time.DateService;
import com.industriallogic.time.DateServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class BasketService {

    private List<Item> items;
    private Set<Promotion> promotions;

    public BasketService(Set<Promotion> promotions) {
        items = new LinkedList<>();
        this.promotions = new HashSet<>(promotions);
    }

    public void add(Items item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            items.add(new Item(item));
        }
    }

    public void add(Items item) {
        items.add(new Item(item));
    }

    public BigDecimal checkout() {
        promotions.forEach(promotion -> promotion.apply(items));
        return calculateTotalPrice();
    }

    private BigDecimal calculateTotalPrice() {
        return items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addItemsAndPrintPrice(String... args) {
        for (String item : args) {
            add(Items.valueOf(item));
        }

        System.out.println("Total price: " + checkout());
    }

    public static void main(String... args) {
        DateService dateService = new DateServiceImpl();
        Set<Promotion> promotions = new HashSet<>();
        promotions.add(new ApplePromotion(dateService));
        promotions.add(new BreadPromotion(dateService));
        BasketService basketService = new BasketService(promotions);
        basketService.addItemsAndPrintPrice(args);
    }

}
