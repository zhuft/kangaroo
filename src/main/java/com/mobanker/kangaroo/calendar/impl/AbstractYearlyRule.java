package com.mobanker.kangaroo.calendar.impl;

import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.redis.CacheKeys;
import com.mobanker.kangaroo.calendar.redis.JRedisClientFactory;

import java.util.Date;


public abstract class AbstractYearlyRule extends AbstractRecurRule {

    protected AbstractMutliCalendarRuleHelper ruleHelper = new GregorianCalenarRuleHelper();

    public AbstractYearlyRule(Event calendar) {
        super(calendar);
    }

    public AbstractYearlyRule(Event calendar, AbstractMutliCalendarRuleHelper ruleHelper) {
        super(calendar);
        this.ruleHelper = ruleHelper;
    }


    @Override
    public void loadOneCycleCache() {
        Date day = ruleHelper.computeNextOccurMonthDay(this, curDay);

        // 下一个周期
        curDay = getNextIntervalYearFirst(curDay, getInterval());

        if (day != null) {
            JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
                    day.getTime(), String.valueOf(day.getTime()));
            times++;
        }

    }

    protected abstract Date getNextIntervalYearFirst(Date theDay, int years);

}
