package com.hsn.resourceloadersample.common;

import java.text.ParseException;

/**
 * Created by hassanshakeel on 2/17/18.
 */

public class Utils {

    public static String fromServerDateTimetoUiDateTime(String dateTime){
        try {
           return Constants.UI_DATE_FORMAT.format(Constants.SERVER_DATE_FORMAT.parse(dateTime));
        } catch (Exception e) {
            e.printStackTrace();
            return dateTime;
        }
    }
}
