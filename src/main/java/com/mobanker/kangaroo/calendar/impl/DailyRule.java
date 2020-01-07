package com.mobanker.kangaroo.calendar.impl;


import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.redis.CacheKeys;
import com.mobanker.kangaroo.calendar.redis.JRedisClientFactory;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
public class DailyRule extends AbstractRecurRule {

    public DailyRule(Event calendar) {
        super(calendar);
    }

    @Override
    public void loadOneCycleCache() {
        JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
            curDay.getTime(), String.valueOf(curDay.getTime()));
        
        curDay = DateTimeUtils.plusDays(curDay, getInterval());
        times++;
        
    }

}
