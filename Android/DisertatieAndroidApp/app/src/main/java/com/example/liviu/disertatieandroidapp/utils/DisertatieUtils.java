package com.example.liviu.disertatieandroidapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DisertatieUtils {

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());

    }
}
