package com.shkoda;

import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.sums.AbstractCheckSum;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.Formatter;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class Main {
    private static final boolean[] message = MessageGenerator.generateMessage(
            new int[]{0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0,});
    private static final int[] errors = {3, 6, 9, 12};

    public static void main(String[] args) {
        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        AbstractCheckSum correctSum = new SigmaCheckSum(message);
        AbstractCheckSum badSum = new SigmaCheckSum(badMessage);

        AbstractCheckSum delta = correctSum.delta(badSum);

        System.out.println(Formatter.toString(message, correctSum, badMessage, badSum, delta, errors));
    }


}
