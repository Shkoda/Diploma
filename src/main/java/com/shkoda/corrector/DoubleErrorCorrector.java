package com.shkoda.corrector;

import com.shkoda.generator.MessageGenerator;
import com.shkoda.utils.MathUtils;

import java.util.List;

import static com.shkoda.sum.CheckSumCounter.count;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 10.04.2015.
 */
@Deprecated
public class DoubleErrorCorrector {

    public static int[] errorPositions(boolean[] message, List<Integer> correctSums) {
        List<Integer> currentSums = count(message);
        List<Integer> delta = xor(correctSums, currentSums);

        int bigIndex = -1, smallIndex = -1;
        int bitNumber = MathUtils.bitNumber(message.length);

        int delta0 = delta.get(0);

        for (int i = delta.size() - 1; i >= 1; i--) {
            int currentDelta = delta.get(i);
            if (currentDelta != 0 && currentDelta != delta0) {
                bigIndex = currentDelta ;

                smallIndex = bigIndex ^ delta.get(0);
                break;
            }

        }
        return new int[]{smallIndex, bigIndex};

    }

    public static boolean[] fix(boolean[] message, List<Integer> correctSums) {
        int[] errorPositions = errorPositions(message, correctSums);
        return MessageGenerator.invertBits(message, errorPositions);
    }


}
