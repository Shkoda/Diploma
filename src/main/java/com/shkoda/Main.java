package com.shkoda;

import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.CheckSum;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class Main {
    private static final boolean[] message = MessageGenerator.generateMessage(
            new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,});
    private static final int[] errors = {1, 3, 5, 7};

    public static void main(String[] args) {
        CheckSum correctSum = CheckSum.build(message);
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);
        CheckSum badSum = CheckSum.build(badMessage);

        CheckSum delta = CheckSum.delta(correctSum, badSum);

        System.out.println(delta);
    }


}
