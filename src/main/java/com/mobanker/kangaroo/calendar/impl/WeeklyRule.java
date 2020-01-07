package com.mobanker.kangaroo.calendar.impl;

import java.util.Date;
import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.field.ByDay;
import com.mobanker.kangaroo.calendar.redis.CacheKeys;
import com.mobanker.kangaroo.calendar.redis.JRedisClientFactory;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import org.joda.time.DateTime;

public class WeeklyRule extends AbstractRecurRule {

    public WeeklyRule(Event event) {
        super(event);
    }

    @Override
    public void loadOneCycleCache() {
        for (ByDay byDay : getByDaySet()) {
            Date day = new DateTime(curDay).withDayOfWeek(byDay.getWeekDay().getIndex()).toDate();
            
            //如果发生时间虽然在本周内,但是weekIndex已过期
            if(DateTimeUtils.compareTo(day, curDay) <= 0){
                continue;
            }
            
            JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
                    day.getTime(), String.valueOf(day.getTime()));
            times++;
        }
        
        //下一个周期
        curDay = DateTimeUtils.theWeekFirst(DateTimeUtils.plusWeeks(curDay, getInterval()));
    }


}
