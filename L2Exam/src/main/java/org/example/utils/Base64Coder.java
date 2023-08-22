package org.example.utils;

import java.util.Base64;

public class Base64Coder {

    public static String decode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}
