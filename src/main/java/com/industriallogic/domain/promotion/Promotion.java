package com.industriallogic.domain.promotion;

import com.industriallogic.domain.Item;
import com.industriallogic.time.DateService;

import java.util.Date;
import java.util.List;

public abstract class Promotion {

    Date startDate;
    Date endDate;
    DateService dateService;

    abstract public void apply(List<Item> items);

    abstract public Date getStartDate();

    abstract public Date getEndDate();
}
