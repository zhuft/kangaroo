package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.Lunar;
import com.mobanker.kangaroo.calendar.utils.LunarMap;
import org.joda.time.DateTime;

public class LunarMonthlyRule extends AbstractMonthlyRule {

    public LunarMonthlyRule(Event calendar) {
        super(calendar);
        this.ruleHelper = new LunarCalenarRuleHelper();
    }

    @Override
    protected Date getNextIntervalMonthFirst(Date theDay, int months) {
        Date day = theDay;
        int i = 0;
        while(i++ < months){
            Lunar theDayL = new Lunar(day).nextMonthFirst();
            day =  new DateTime(LunarMap.getDate(theDayL)).toDate();
        }
        return theDay;
    }

}
