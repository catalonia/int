package com.tastesync.push.util;

public class CommonFunctionsUtil {
    public static String getModifiedValueString(String inputString) {
        return ((inputString == null) ? "" : inputString.trim());

        //return inputString;
    }
}
