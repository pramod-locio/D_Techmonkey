package com.techmonkey.topyaps.helper;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.text.ParseException;
/**
 * Created by P KUMAR on 27-12-2016.
 */

public class DateTimeFormat {

    public static String DateTimeFormat(String inputDate){
        String oldDate,str1,str2,newDate = null;
        Date date;

        DateFormat inputDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            oldDate=inputDate.replace('T',' ');
            date=inputDateFormat.parse(oldDate);
            str1=DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
            str2=DateFormat.getDateInstance().format(date);
            newDate=str1+" "+str2;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
