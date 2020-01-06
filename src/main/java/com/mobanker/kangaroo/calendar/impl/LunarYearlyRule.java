package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.Lunar;
import com.mobanker.kangaroo.calendar.utils.LunarMap;
import org.joda.time.DateTime;

public class LunarYearlyRule extends AbstractYearlyRule {

    public LunarYearlyRule(Event calendar) {
        super(calendar);
    }


    @Override
    protected Date getNextYearFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        return new DateTime(LunarMap.getDate(new Lunar(theDayL.getYear() + 1, 1, 1, false))).toDate();
    }

    @Override
    protected Date getNextYearMonthFirst(Date theDay) {
        Lunar theDayL = new Lunar(theDay);
        int year = theDayL.getYear() + 1;
        int month = getByMonthSet().iterator().next();
        Lunar theNextYearMonthFirstL = new Lunar(year, month, 1, false);
        return new DateTime(LunarMap.getDate(theNextYearMonthFirstL)).withMillisOfDay(getStartTime() * 60 * 1000).toDate();
        
    }

    @Override
    public Date computeTheLastCountOccurDate() {
        return null;
    }


}
