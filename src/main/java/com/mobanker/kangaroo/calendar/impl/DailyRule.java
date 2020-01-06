package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import org.joda.time.DateTime;
public class DailyRule extends AbstractRecurRule {


    public DailyRule(Event calendar) {
        super(calendar);
    }

    @Override
    public Date computeNextOccurDate(Date offsetDate) {
        Date startDateTime = getStartDateTime();
        int periodDays = DateTimeUtils.periodDays(startDateTime, offsetDate);
        int nextDays = periodDays + (getInterval() - periodDays % getInterval());
        return DateTimeUtils.plusDays(startDateTime, nextDays);
    }

    @Override
    public Date computeTheLastCountOccurDate() {
        Date recurEndDate = null;
        if(getUntil() != null){
            recurEndDate = getUntil();
        }else if(getCount() != null){
            int days = (getCount() - 1) * getInterval();
            recurEndDate = new DateTime(getStartDateTime()).plusDays(days).toDate();
        }
        return recurEndDate;
        
    }

}
