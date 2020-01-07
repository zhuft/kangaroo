package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.Lunar;
import com.mobanker.kangaroo.calendar.utils.LunarMap;
import org.joda.time.DateTime;

public class LunarYearlyRule extends AbstractYearlyRule {

    public LunarYearlyRule(Event calendar) {
        super(calendar);
        this.ruleHelper = new LunarCalenarRuleHelper();
    }

    @Override
    protected Date getNextIntervalYearFirst(Date theDay, int years) {
        Lunar theDayL = new Lunar(theDay);
        int year = theDayL.getYear() + years;
        int month = getByMonthSet().iterator().next();
        Lunar theNextYearMonthFirstL = new Lunar(year, month, 1, false);
        return new DateTime(LunarMap.getDate(theNextYearMonthFirstL)).withMillisOfDay(getStartTime() * 60 * 1000).toDate();
    }


}
