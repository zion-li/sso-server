package com.myself.ssoserver.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Created by zion
 * @Date 2019/8/21.
 */
public class CalendarUtil {

    private static Calendar c = Calendar.getInstance();

    public static boolean isToday(Date inputDate) {
        long current = System.currentTimeMillis();
        long zero = current - (current + TimeZone.getDefault().getRawOffset()) % 86400000;
        return inputDate.getTime() > zero;
    }
}
