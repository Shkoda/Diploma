package com.shkoda.generator;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class MessageGenerator {
    public static boolean[] generateMessage(int length) {
        boolean[] message = new boolean[length];
        for (int i = 0; i < length; i++)
            message[i] = Math.random() > 0.5;
        return message;
    }

    public static boolean[] damagedMessage(boolean[] message, int... positions) {
        boolean[] damaged = new boolean[message.length];
        System.arraycopy(message, 0, damaged, 0, message.length);
        for (int position : positions) {
            damaged[position] = !message[position];
        }
        return damaged;
    }
}
