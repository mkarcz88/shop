package com.industriallogic.domain.promotion;

import com.industriallogic.domain.Item;
import com.industriallogic.domain.Items;
import com.industriallogic.time.DateService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.industriallogic.domain.Items.BREAD;
import static java.util.Calendar.DAY_OF_MONTH;

public class BreadPromotion extends Promotion {

    private static final BigDecimal PROMOTIONAL_PRICE = new BigDecimal("0.5");

    public BreadPromotion(DateService dateService) {
        this.dateService = dateService;
        calculateStartAndEndDate();
    }

    @Override
    public void apply(List<Item> items) {
        if (startDate.before(dateService.getDate()) && endDate.after(dateService.getDate())) {
            long breadsEligibleForPromotion = items.stream()
                    .filter(item -> Items.SOUP.getName().equals(item.getName()))
                    .count() / 2;

            BigDecimal breadNewPrice = BREAD.getPrice().multiply(PROMOTIONAL_PRICE);

            items.stream()
                    .filter(item -> BREAD.getName().equals(item.getName()))
                    .limit(breadsEligibleForPromotion)
                    .forEach(bread -> bread.setPrice(breadNewPrice));
        }
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    private void calculateStartAndEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateService.getDate());
        calendar.add(DAY_OF_MONTH, -1);
        this.startDate = calendar.getTime();

        calendar.add(DAY_OF_MONTH, 7);
        this.endDate = calendar.getTime();
    }
}
