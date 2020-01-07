package com.mobanker.kangaroo.calendar.redis;


import com.mobanker.kangaroo.calendar.Event;

public class CacheKeys {

    public static String getRecurEventKey(Event event){
        return "recure_event_list" + event.getId();
    }
    
}
