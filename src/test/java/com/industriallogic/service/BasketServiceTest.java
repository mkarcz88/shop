package com.industriallogic.service;

import com.industriallogic.domain.promotion.ApplePromotion;
import com.industriallogic.domain.promotion.BreadPromotion;
import com.industriallogic.domain.promotion.Promotion;
import com.industriallogic.time.DateService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.industriallogic.domain.Items.APPLES;
import static com.industriallogic.domain.Items.BREAD;
import static com.industriallogic.domain.Items.MILK;
import static com.industriallogic.domain.Items.SOUP;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasketServiceTest {
    private BasketService basketService;
    private Set<Promotion> promotions = new HashSet<>();
    private DateService dateService = mock(DateService.class);


    @Before
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 15);
        Date date = calendar.getTime();

        when(dateService.getDate()).thenReturn(date);

        promotions.add(new ApplePromotion(dateService));
        promotions.add(new BreadPromotion(dateService));
        basketService = new BasketService(promotions);
    }

    @Test
    public void validateTotalPriceCase1() {
        basketService.add(SOUP, 3);
        basketService.add(BREAD, 2);
        assertEquals(new BigDecimal("3.15"), basketService.checkout());
    }

    @Test
    public void validateTotalPriceCase2() {
        basketService.add(APPLES, 6);
        basketService.add(MILK);
        assertEquals(new BigDecimal("1.90"), basketService.checkout());
    }

    @Test
    public void validateTotalPriceCase3() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateService.getDate());
        calendar.add(DAY_OF_MONTH, 5);
        when(dateService.getDate()).thenReturn(calendar.getTime());
        basketService.add(APPLES, 6);
        basketService.add(MILK);
        assertEquals(new BigDecimal("1.84"), basketService.checkout());
    }

    @Test
    public void validateTotalPriceCase4() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateService.getDate());
        calendar.add(DAY_OF_MONTH, 5);
        when(dateService.getDate()).thenReturn(calendar.getTime());
        basketService.add(APPLES, 3);
        basketService.add(SOUP, 2);
        basketService.add(BREAD, 1);
        assertEquals(new BigDecimal("1.97"), basketService.checkout());
    }


}
