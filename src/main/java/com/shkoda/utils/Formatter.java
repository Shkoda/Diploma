package com.shkoda.utils;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Formatter {
    public static String toString(boolean[] message) {
        StringBuilder sb = new StringBuilder();
        for (boolean b : message) {
            sb.append(b ? 1 : 0).append(" ");
        }
        return sb.toString();
    }
}
