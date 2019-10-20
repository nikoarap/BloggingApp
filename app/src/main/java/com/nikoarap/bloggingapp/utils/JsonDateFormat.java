package com.nikoarap.bloggingapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateFormat {

    private static String jsonDate;
    private static Date date;
    private static String stringDate;

    public JsonDateFormat() {

    }

    private static String removeFromTheEnd(String str, int x) {
        return str.substring(0, str.length() - x);
    }

    public String convertJsonDateToNormal(String jsonDate){
        String s = removeFromTheEnd(jsonDate,5);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        stringDate = removeFromTheEnd(date.toString(), 14);
        return stringDate;
    }

}
