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

import static com.industriallogic.domain.Items.BREAD;
import static com.industriallogic.domain.Items.SOUP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BreadPromotionTest {

    private BreadPromotion breadPromotion;
    private DateService dateService;

    @Before
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 15);
        Date date = calendar.getTime();
        dateService = mock(DateService.class);
        when(dateService.getDate()).thenReturn(date);
        breadPromotion = new BreadPromotion(dateService);
    }

    @Test
    public void checkStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 14);
        Date date = calendar.getTime();
        assertEquals(date.getTime(), breadPromotion.getStartDate().getTime(), 100);
    }

    @Test
    public void checkEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 10, 21);
        Date date = calendar.getTime();
        assertEquals(date.getTime(), breadPromotion.getEndDate().getTime(), 100);
    }

    @Test
    public void applyWhenNotEligibleForPromotion() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 10, 15);
        Date date = calendar.getTime();
        when(dateService.getDate()).thenReturn(date);
        List<Item> items = Arrays.asList(new Item(BREAD), new Item(SOUP), new Item(SOUP));
        breadPromotion.apply(items);
        assertEquals(BREAD.getPrice(), items.get(0).getPrice());
        assertEquals(SOUP.getPrice(), items.get(1).getPrice());
        assertEquals(SOUP.getPrice(), items.get(2).getPrice());
    }

    @Test
    public void applyWhenNothingEligibleForPromotion() {
        List<Item> items = Arrays.asList(new Item(BREAD), new Item(SOUP));
        breadPromotion.apply(items);
        assertEquals(BREAD.getPrice(), items.get(0).getPrice());
        assertEquals(SOUP.getPrice(), items.get(1).getPrice());
    }

    @Test
    public void applyWhenOneBreadEligibleForPromotion() {
        List<Item> items = Arrays.asList(new Item(BREAD), new Item(SOUP), new Item(SOUP), new Item(SOUP));
        breadPromotion.apply(items);
        assertEquals(BREAD.getPrice().divide(new BigDecimal("2")), items.get(0).getPrice());
        assertEquals(SOUP.getPrice(), items.get(1).getPrice());
        assertEquals(SOUP.getPrice(), items.get(2).getPrice());
        assertEquals(SOUP.getPrice(), items.get(3).getPrice());
    }

    @Test
    public void applyWhenTwoBreadEligibleForPromotion() {
        List<Item> items = Arrays.asList(new Item(BREAD), new Item(BREAD), new Item(SOUP), new Item(SOUP), new Item(SOUP), new Item(SOUP));
        breadPromotion.apply(items);
        assertEquals(BREAD.getPrice().divide(new BigDecimal("2")), items.get(0).getPrice());
        assertEquals(BREAD.getPrice().divide(new BigDecimal("2")), items.get(1).getPrice());
        assertEquals(SOUP.getPrice(), items.get(2).getPrice());
        assertEquals(SOUP.getPrice(), items.get(3).getPrice());
    }
}
