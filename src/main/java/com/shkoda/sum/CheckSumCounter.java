package com.shkoda.sum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.utils.MathUtils.*;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class CheckSumCounter {

    public static List<Integer> count(boolean[] message) {
        int bitNumber = bitNumber(message.length);
        List<Integer> sums = new ArrayList<>(bitNumber + 1);

        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        sums.add(xor(oneBitIndexes));

        for (int i = 0; i < bitNumber; i++) {
            sums.add(xor(filterOneBitIndexesWithOneOnPosition(message, i)));
        }

        return sums;
    }

    public static List<Integer> countWithThird(boolean[] message) {
        int bitNumber = bitNumber(message.length);
        List<Integer> sums = new ArrayList<>();

        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);


        sums.add(xor(oneBitIndexes));
        sums.add(xor(oneBitIndexes.stream()
                .filter(i -> i % 3 == 0)
                .collect(Collectors.toList())));

//        sums.add(xorSquared(oneBitIndexes));
//        sums.add(xorCubed(oneBitIndexes));

        for (int i = 0; i < bitNumber; i++) {
            List<Integer> oneBitIndexesWithOneOnPosition = filterOneBitIndexesWithOneOnPosition(message, i);
            sums.add(xor(oneBitIndexesWithOneOnPosition));
//            sums.add(xor(squared(oneBitIndexesWithOneOnPosition)));
        }

        return sums;
    }


    public static List<Integer> countShifted(boolean[] message) {
        int bitNumber = bitNumber(message.length);
        List<Integer> sums = new ArrayList<>(bitNumber + 1);

        sums.add(xor(filterAllOneBitIndexes(message)));

        for (int i = 0; i < bitNumber; i++) {
            sums.add(xor(filterOneBitIndexesWithOneOnPosition(message, i)));
        }

        return sums;
    }


    private static List<Integer> filterAllOneBitIndexesAndShiftThird(boolean[] message) {
        List<Integer> list = filterAllXBitIndexes(message, true);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 3 == 0) {
                list.set(i, list.get(i) << 1);
            }

        }
        return list;
    }

    private static int shiftIfDividable(final int number, final int divider) {
        if (number % divider == 0) return number << 1;
        return number;
    }

    public static List<Integer> filterAllOneBitIndexes(boolean[] message) {
        return filterAllXBitIndexes(message, true);
    }

    private static List<Integer> filterAllZeroBitIndexes(boolean[] message) {
        return filterAllXBitIndexes(message, false);
    }

    private static List<Integer> filterAllXBitIndexes(boolean[] message, boolean bitValue) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            if (message[i] == bitValue)
                indexes.add(i + 1);
        }
        return indexes;
    }

    private static int shiftRight(int value, int bitNumber) {
        int distance = value % 4;

//        int distance = 2;
        return ((value >>> distance) | (value << (bitNumber - distance))) & (~(1 << bitNumber));
    }

    private static int shiftLeft(int value, int bitNumber) {
        int distance = value % 4;
//        int distance = 3;
        return ((value << distance) | (value >>> (bitNumber - distance))) & (~(1 << bitNumber));
    }


    private static List<Integer> filterAllOneBitIndexesShifted(boolean[] message) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            if (message[i])
                indexes.add(i + 1);
        }
        return indexes;
    }


    public static List<Integer> filterOneBitIndexesWithOneOnPosition(boolean[] message, int oneBitPositionNumber) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            if (message[i] && hasOneBitOnPosition(i + 1, oneBitPositionNumber))
                indexes.add(i + 1);

        }
        return indexes;
    }
}
