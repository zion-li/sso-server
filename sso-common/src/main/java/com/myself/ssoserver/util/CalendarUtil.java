package com.myself.ssoserver.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Created by zion
 * @Date 2019/8/21.
 */
public class CalendarUtil {

    private static Calendar c = Calendar.getInstance();

    public static boolean isToday(Date inputDate) {
        Date now = new Date(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
        return inputDate.after(now);
    }
}
