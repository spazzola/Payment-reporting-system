package com.utility;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {


    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

    public static Date toDate(String date) {
        try {
            return new Date(sdf1.parse(date).getTime());
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public static String toString(Date date) {
        return sdf1.format(date);
    }

    public static Date incrementMonth(Date date) {
        return DateUtils.addMonths(date, 1);
    }

}