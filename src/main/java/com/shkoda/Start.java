package com.shkoda;

import com.shkoda.corrector.TripleErrorCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.sum.CheckSum;
import com.shkoda.utils.MathUtils;

import java.util.List;

import static com.shkoda.utils.Formatter.logError;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Start {
    public static void main(String[] args) {
        //{0,1,1,0,1,1,0}
        boolean[] message = MessageGenerator.generateMessage(new int[]{
                0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0});
        List<Integer> correctSum = CheckSum.count(message);

//        int[] errors = new int[]{4,6,12,14};
        int[] errors = new int[]{1, 3, 5, 7};

        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        List<Integer> badSum = CheckSum.count(badMessage);

        List<Integer> delta = MathUtils.xor(correctSum, badSum);
        boolean[] fixed = null;
        try {
            int[] errorIndexes = TripleErrorCorrector.solve(delta);
            fixed = MessageGenerator.invertBits(badMessage, errorIndexes);

        } catch (Exception e) {

        } finally {
            logError(message, badMessage, fixed, badSum, correctSum, delta, errors);
        }

    }
}
