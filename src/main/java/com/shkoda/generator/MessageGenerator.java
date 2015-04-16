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
            list.add(i+1);
        Collections.shuffle(list);
        int[] numbers = new int[amount];
        for (int i = 0; i < amount; i++)
            numbers[i] = list.get(i);
        Arrays.sort(numbers);
        return numbers;

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
