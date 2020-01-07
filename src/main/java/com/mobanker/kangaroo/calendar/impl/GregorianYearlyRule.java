package com.mobanker.kangaroo.calendar.impl;

import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import java.util.Date;

public class GregorianYearlyRule extends AbstractYearlyRule {

    public GregorianYearlyRule(Event calendar) {
        super(calendar);
    }

    @Override
    protected Date getNextIntervalYearFirst(Date theDay, int years) {
        return DateTimeUtils.plusYears(theDay, years);
    }

}
