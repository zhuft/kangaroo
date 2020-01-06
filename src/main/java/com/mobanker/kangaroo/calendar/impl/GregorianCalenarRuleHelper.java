package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import org.joda.time.DateTime;

public class GregorianCalenarRuleHelper extends AbstractMutliCalendarRuleHelper {

    @Override
    protected int getMaxDayOfMonth(Date theDay) {
        return new DateTime(theDay).dayOfMonth().getMaximumValue();
    }
    
    @Override
    protected int getMonthFirstDayOfWeek(Date theDay) {
        return new DateTime(DateTimeUtils.theMonthFirst(theDay)).getDayOfWeek();
    }
    
    @Override
    protected int getMonthEndDayOfWeek(Date theDay) {
        int monthEndDayOfWeek = new DateTime(DateTimeUtils.theMonthEnd(theDay)).getDayOfWeek();
        return monthEndDayOfWeek;
    }
}
