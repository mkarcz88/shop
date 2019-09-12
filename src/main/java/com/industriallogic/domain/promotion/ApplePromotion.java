package com.industriallogic.domain.promotion;

import com.industriallogic.domain.Item;
import com.industriallogic.domain.Items;
import com.industriallogic.time.DateService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.industriallogic.domain.Items.APPLES;
import static java.util.Calendar.DAY_OF_MONTH;

public class ApplePromotion extends Promotion {

    private final BigDecimal PROMOTIONAL_PRICE = new BigDecimal("0.9");

    public ApplePromotion(DateService dateService) {
        this.dateService = dateService;
        calculateStartAndEndDate();
    }

    @Override
    public void apply(List<Item> items) {
        if (startDate.before(dateService.getDate()) && endDate.after(dateService.getDate())) {
            BigDecimal appleNewPrice = Items.APPLES.getPrice().multiply(PROMOTIONAL_PRICE);
            items.stream()
                    .filter(item -> APPLES.getName().equals(item.getName()))
                    .forEach(apple -> apple.setPrice(appleNewPrice));
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
        calendar.add(DAY_OF_MONTH, 3);
        this.startDate = calendar.getTime();

        calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        this.endDate = calendar.getTime();
    }

}
