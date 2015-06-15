package com.abc;

import java.util.Date;
import java.util.Calendar;

public class DateProvider {
    private static final DateProvider instance = new DateProvider();

    private DateProvider() {
    }

    public static DateProvider getInstance() {
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
