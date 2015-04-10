package com.shkoda.generator;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class MessageGenerator {
    public static boolean[] generateMessage(int[] prototype) {
        boolean[] message = new boolean[prototype.length];
        for (int i = 0; i < prototype.length; i++) {
            message[i] = prototype[i] == 1;
        }
        return message;

    }

    public static boolean[] generateMessage(int length) {
        boolean[] message = new boolean[length];
        for (int i = 0; i < length; i++)
            message[i] = Math.random() > 0.5;
        return message;
    }

    public static boolean[] invertBits(boolean[] message, int... positions) {
        boolean[] modified = new boolean[message.length];
        System.arraycopy(message, 0, modified, 0, message.length);
        for (int position : positions) {
            modified[position] = !message[position];
        }
        return modified;
    }
}
