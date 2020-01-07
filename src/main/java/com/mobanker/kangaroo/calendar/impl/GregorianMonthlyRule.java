package com.mobanker.kangaroo.calendar.impl;

import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import java.util.Date;

public class GregorianMonthlyRule extends AbstractMonthlyRule {

    public GregorianMonthlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextIntervalMonthFirst(Date theDay, int months) {
        return DateTimeUtils.theMonthFirst(DateTimeUtils.plusMonths(theDay, months));
    }


}
