package com.shkoda.utils;

import com.shkoda.corrector.TripleErrorCorrector;
import com.shkoda.structures.sums.AbstractCheckSum;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Formatter {
    public static String toString(boolean[] correctMessage, AbstractCheckSum correctSum, boolean[] badMessage, AbstractCheckSum badSum, AbstractCheckSum delta, int[] realBadIndexes){
        StringBuilder sb = new StringBuilder();
        sb.append("Original :: ").append(Formatter.toString(correctMessage)).append("\n");
        sb.append("Damaged  :: ").append(Formatter.toString(badMessage)).append("\n\n");

        sb.append("Control sums :: ").append(correctSum).append("\n");
        sb.append("Bad sums     :: ").append(badSum).append("\n");
        sb.append("Delta sums   :: ").append(delta).append("\n\n");

        sb.append("Real bad positions  :: ").append(Arrays.toString(realBadIndexes)).append("\t").append(Formatter.toBinaryString(realBadIndexes)).append("\n");
//        sb.append("Found bad positions :: ").append(Arrays.toString(TripleErrorCorrector.errorPositions(badMessage, sum))).append("\n");
//        sb.append("Found bad positions :: ").append(Arrays.toString(TripleErrorCorrector.solve(MathUtils.xor(sum, badSum)))).append("\n");
        return sb.toString();
    }


    public static String toString(boolean[] message) {
        if (message == null)
            return "null";
        StringBuilder sb = new StringBuilder();
        for (boolean b : message) {
            sb.append(b ? 1 : 0).append(", ");
        }
        return sb.toString();
    }

    public static void logError(boolean[] message, boolean[] badMessage, boolean[] fixed, List<Integer> badSum, List<Integer> sum, List<Integer> integers, int... errorIndexes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Original :: ").append(Formatter.toString(message)).append("\n");
        sb.append("Damaged  :: ").append(Formatter.toString(badMessage)).append("\n");
        sb.append("Fixed    :: ").append(Formatter.toString(fixed)).append("\n\n");


        sb.append("Control sums :: ").append(sum).append("\n");
        sb.append("Bad sums     :: ").append(badSum).append("\n");
        sb.append("Delta sums   :: ").append(MathUtils.xor(sum, badSum)).append("\n\n");

        sb.append("Real bad positions  :: ").append(Arrays.toString(errorIndexes)).append("\t").append(Formatter.toBinaryString(errorIndexes)).append("\n");
//        sb.append("Found bad positions :: ").append(Arrays.toString(TripleErrorCorrector.errorPositions(badMessage, sum))).append("\n");
        sb.append("Found bad positions :: ").append(Arrays.toString(TripleErrorCorrector.solve(MathUtils.xor(sum, badSum)).toArray())).append("\n");


        System.err.print(sb.toString());
    }

    public static void logError(boolean[] message, boolean[] badMessage, boolean[] fixed, List<Integer> badSum, List<Integer> sum, List<Integer> delta, int[] errorIndexes, int[] foundErrorIndexes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Original :: ").append(Formatter.toString(message)).append("\n");
        sb.append("Damaged  :: ").append(Formatter.toString(badMessage)).append("\n");
        sb.append("Fixed    :: ").append(Formatter.toString(fixed)).append("\n\n");


        sb.append("Control sums :: ").append(sum).append("\n");
        sb.append("Bad sums     :: ").append(badSum).append("\n");
        sb.append("Delta sums   :: ").append(delta).append("\n\n");

        sb.append("Real bad positions  :: ").append(Arrays.toString(errorIndexes)).append("\n");
//        sb.append("Found bad positions :: ").append(Arrays.toString(TripleErrorCorrector.errorPositions(badMessage, sum))).append("\n");
        sb.append("Found bad positions :: ").append(Arrays.toString(foundErrorIndexes)).append("\n");


        System.err.print(sb.toString());
    }
    public static String toBinaryString(int value){
        return toBinaryString(value, MathUtils.bitNumber(value));
    }
    public static String toBinaryString(int value, int bitNumber){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< bitNumber; i++)
            sb.append(value >> (bitNumber - i - 1) & 1);
        return sb.toString();
    }

    public static String toBinaryString( int[] values){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int bitNumber = MathUtils.bitNumber(values[values.length - 1]);
        for (int value : values)
            sb.append(toBinaryString(value, bitNumber)).append(" ");
        sb.append("]");
        return sb.toString();
    }

}
