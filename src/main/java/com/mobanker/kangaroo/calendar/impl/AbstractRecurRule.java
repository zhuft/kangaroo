package com.mobanker.kangaroo.calendar.impl;

import com.mobanker.kangaroo.calendar.Event;
import com.mobanker.kangaroo.calendar.RuleFactory;
import com.mobanker.kangaroo.calendar.field.ByDay;
import com.mobanker.kangaroo.calendar.redis.CacheKeys;
import com.mobanker.kangaroo.calendar.redis.JRedisClientFactory;
import com.mobanker.kangaroo.calendar.redis.RecurCacheWindowSize;
import com.mobanker.kangaroo.calendar.utils.DateTimeUtils;
import java.util.Date;
import java.util.Set;

public abstract class AbstractRecurRule extends AbstractRule {

    private Date until;
    private Integer count;
    private Integer interval;
    private Set<ByDay> byDaySet;
    private Set<Integer> byMonthDaySet;
    private Set<Integer> byYearDaySet;
    private Set<Integer> byWeekNoSet;
    private Set<Integer> byMonthSet;

    private RecurCacheWindowSize cacheWindowSize;
    protected Date curDay;
    protected int times;

    public AbstractRecurRule(final Event event) {
        super(event);
        curDay = new Date(getStartDateTime().getTime());//deep copy
        parse();
    }

    // 解析rule字符串规则
    private void parse() {
        String ruleStr = event.getRule();
        // until
        until = RuleFactory.extractUntilDate(ruleStr);
        // count
        count = RuleFactory.extractCount(ruleStr);
        // interval
        interval = RuleFactory.extractInterval(ruleStr);
        // byDaySet
        byDaySet = RuleFactory.extractByDaySet(ruleStr);
        // byMonthDaySet
        byMonthDaySet = RuleFactory.extractByMonthDaySet(ruleStr);
        // byYearDaySet
        byYearDaySet = RuleFactory.extractByYearDaySet(ruleStr);
        // byWeekNoset
        byWeekNoSet = RuleFactory.extractByWeekNoSet(ruleStr);
        // byMonthSet
        byMonthSet = RuleFactory.extractByMonthSet(ruleStr);
    }

    @Override
    public boolean includes(Date theDate) {
        loadCacheSize();

        return JRedisClientFactory.getJRedisClient().zrank(CacheKeys.getRecurEventKey(event),
                String.valueOf(theDate.getTime())) != null;
    }

    @Override
    public Date nextOccurDate(Date offsetDate) {
        loadCacheSize();

        Set<String> nextDateStrSet =
                JRedisClientFactory.getJRedisClient().zrangeByScore(
                        CacheKeys.getRecurEventKey(event), String.valueOf(offsetDate.getTime()),
                        "+inf", 0, 1);
        if (nextDateStrSet.isEmpty()) {
            return null;
        }
        return new Date(Long.valueOf(nextDateStrSet.iterator().next()));
    }


    public void loadCacheSize() {

        boolean existKey =
                JRedisClientFactory.getJRedisClient().exists(CacheKeys.getRecurEventKey(event));
        if (existKey) return;

        while (true) {
            boolean isValidDay = true;
            if (getUntil() != null) {
                isValidDay = DateTimeUtils.compareTo(curDay, getUntil()) <= 0;
            } else if (getCount() != null) {
                isValidDay = times < getCount();
            }

            // 发生日期超出界限
            if (!(isValidDay && getCacheWindowSize().inWindow(curDay))) {
                break;
            }

            // JRedisClientFactory.getJRedisClient().zadd(CacheKeys.getRecurEventKey(event),
            // occurDay.getTime(), String.valueOf(occurDay.getTime()));
            // occurDay = computNextOccurDay(occurDay, occurIndex++);
            
            loadOneCycleCache();
            
            
        }
    }

    public abstract void loadOneCycleCache();

    public Event getCalendar() {
        return event;
    }

    public Date getUntil() {
        return until;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getInterval() {
        if (interval == null) {
            return 1;
        }
        return interval;
    }

    public Set<ByDay> getByDaySet() {
        return byDaySet;
    }

    public Set<Integer> getByMonthDaySet() {
        return byMonthDaySet;
    }

    public Set<Integer> getByYearDaySet() {
        return byYearDaySet;
    }

    public Set<Integer> getByWeekNoSet() {
        return byWeekNoSet;
    }

    public Set<Integer> getByMonthSet() {
        return byMonthSet;
    }

    public RecurCacheWindowSize getCacheWindowSize() {
        return cacheWindowSize;
    }

    public void setCacheWindowSize(RecurCacheWindowSize cacheWindowSize) {
        this.cacheWindowSize = cacheWindowSize;
    }


}
