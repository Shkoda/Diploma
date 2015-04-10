package com.shkoda.corrector;

import com.shkoda.generator.MessageGenerator;

import java.util.Arrays;
import java.util.List;

import static com.shkoda.sum.CheckSum.count;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class DoubleErrorCorrector {

    private static int[] errorPositions(boolean[] message, List<Integer> correctSums) {
        List<Integer> currentSums = count(message);
        List<Integer> delta = xor(correctSums, currentSums);

        int bigIndex = -1, smallIndex = -1;

        for (int i = delta.size() - 1; i >= 0; i--) {
            if (delta.get(i) != 0) {
                bigIndex = delta.get(i);
                smallIndex = delta.get(i) ^ delta.get(0);
                break;
            }

        }
        return new int[]{smallIndex, bigIndex};


    }

    public static boolean[] fix(boolean[] message, List<Integer> correctSums) {
        int[] errorPositions = errorPositions(message, correctSums);
        System.out.println("Errors : " + Arrays.toString(errorPositions));

        return MessageGenerator.invertBits(message, errorPositions);

    }


}
