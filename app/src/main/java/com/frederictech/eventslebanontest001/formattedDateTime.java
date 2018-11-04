package com.frederictech.eventslebanontest001;

import android.content.Context;
import android.net.ParseException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Frederic on 01-05-2017.
 */

public final class formattedDateTime {

    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        java.sql.Date date = null;
        if (timeToFormat != null) {
            try {
                date = (Date) iso8601Format.parse(timeToFormat);
            } catch (java.text.ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }

    public static java.util.Date convertEventDate(String s) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = (Date) simpleDateFormat.parse(s);
            return date;
        } catch (java.text.ParseException ex) {

        }
        return new java.util.Date();
    }
}
