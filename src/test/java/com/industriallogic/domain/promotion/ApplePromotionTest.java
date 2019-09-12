package com.industriallogic.domain.promotion;

import com.industriallogic.domain.Item;
import com.industriallogic.time.DateService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.industriallogic.domain.Items.APPLES;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplePromotionTest {

    private DateService dateService = mock(DateService.class);
    private ApplePromotion applePromotion;

    @Before
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 15);
        Date date = calendar.getTime();
        when(dateService.getDate()).thenReturn(date);
        applePromotion = new ApplePromotion(dateService);
    }

    @Test
    public void checkStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 18);
        Date date = calendar.getTime();
        assertEquals(date.getTime(), applePromotion.getStartDate().getTime(), 100);
    }

    @Test
    public void checkEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 11, 01, 0, 0);
        Date date = calendar.getTime();
        assertEquals(date.getTime(), applePromotion.getEndDate().getTime(), 100);
    }

    @Test
    public void applyWhenNotEligibleForPromotion() {
        List<Item> items = Arrays.asList(new Item(APPLES), new Item(APPLES));
        applePromotion.apply(items);
        assertEquals(new BigDecimal("0.10"), items.get(0).getPrice());
        assertEquals(new BigDecimal("0.10"), items.get(1).getPrice());
    }

    @Test
    public void applyWhenEligibleForPromotion() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 20);
        Date date = calendar.getTime();
        when(dateService.getDate()).thenReturn(date);
        List<Item> items = Arrays.asList(new Item(APPLES), new Item(APPLES));
        applePromotion.apply(items);
        assertEquals(new BigDecimal("0.09"), items.get(0).getPrice());
        assertEquals(new BigDecimal("0.09"), items.get(1).getPrice());
    }

}
