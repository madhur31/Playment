package com.example.madhurarora.playment.Utils;

/**
 * Created by madhur.arora on 28/05/16.
 */
public class StringUtils {
    public static boolean isNullorEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null");
    }
}
