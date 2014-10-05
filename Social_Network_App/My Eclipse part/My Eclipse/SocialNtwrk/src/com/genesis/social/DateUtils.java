package com.genesis.newzonia;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils
{

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public DateUtils()
    {
    }

    public static String now()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static void main(String arg[])
    {
        System.out.println((new StringBuilder("Now : ")).append(now()).toString());
    }
}
