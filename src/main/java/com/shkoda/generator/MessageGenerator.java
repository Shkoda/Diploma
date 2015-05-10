package com.shkoda.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


    public static int[] generateSortedDifferentNumbers(int bound, int amount) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < bound; i++)
            list.add(i + 1);
        Collections.shuffle(list);
        int[] numbers = new int[amount];
        for (int i = 0; i < amount; i++)
            numbers[i] = list.get(i);
        Arrays.sort(numbers);
        return numbers;

    }

    public static boolean[] generateZeroMessage(int length) {
        return generateValueeMessage(length, false);
    }

    public static boolean[] generateOneMessage(int length) {
        return generateValueeMessage(length, true);
    }

    public static boolean hasNext(boolean[] array){
        for (boolean b : array) {
            if (!b)
                return true;
        }
        return false;
    }

    public static boolean[] next(boolean[] previous) {
        boolean[] next = new boolean[previous.length];
        System.arraycopy(previous, 0, next, 0, previous.length);

        int pointer = previous.length - 1;

        boolean overhead;
        do {
            overhead = next[pointer];
            next[pointer] = !next[pointer];
            pointer--;
        } while (overhead && pointer >= 0);

        return next;
    }


    private static boolean[] generateValueeMessage(int length, boolean value) {
        boolean[] message = new boolean[length];
        for (int i = 0; i < length; i++)
            message[i] = value;
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
            modified[position - 1] = !message[position - 1];
        }
        return modified;
    }
}
