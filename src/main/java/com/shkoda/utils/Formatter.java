package com.shkoda.utils;

import com.shkoda.corrector.DoubleErrorCorrector;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Formatter {
    public static String toString(boolean[] message) {
        if (message == null)
            return "null";
        StringBuilder sb = new StringBuilder();
        for (boolean b : message) {
            sb.append(b ? 1 : 0).append(", ");
        }
        return sb.toString();
    }

    public static void logError(boolean[] message, boolean[] badMessage, boolean[] fixed, List<Integer> sum, List<Integer> badSum, int first, int second) {
        StringBuilder sb = new StringBuilder();
        sb.append("Original :: ").append(Formatter.toString(message)).append("\n");
        sb.append("Damaged  :: ").append(Formatter.toString(badMessage)).append("\n");
        sb.append("Fixed    :: ").append(Formatter.toString(fixed)).append("\n\n");


        sb.append("Control sums :: ").append(sum).append("\n");
        sb.append("Bad sums     :: ").append(badSum).append("\n");
        sb.append("Delta sums   :: ").append(MathUtils.xor(sum, badSum)).append("\n\n");

        sb.append("Real bad positions  :: [").append(first).append(", ").append(second).append("]\n");
        sb.append("Found bad positions :: ").append(Arrays.toString(DoubleErrorCorrector.errorPositions(badMessage, sum))).append("\n");


        System.err.print(sb.toString());
    }
}
