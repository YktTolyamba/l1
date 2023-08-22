package org.example.utils;

public class StringUtils {

    public static int getNumberFromString(String s, String delimiter) {
        return Integer.parseInt(s.substring(s.lastIndexOf(delimiter) + 1));
    }
}
