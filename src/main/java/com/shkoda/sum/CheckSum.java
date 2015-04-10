package com.shkoda.sum;

import java.util.ArrayList;
import java.util.List;
import static com.shkoda.utils.MathUtils.*;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class CheckSum {
    public static List<Integer> count(boolean[] message){
        int bitNumber = bitNumber(message.length);
        List<Integer> sums = new ArrayList<>(bitNumber+1);

        sums.add(xor(filterAllOneBitIndexes(message)));

        for (int i = 0; i<bitNumber; i++){
            sums.add(xor(filterOneBitIndexesWithOneOnPosition(message, i)));
        }

        return sums;
    }


    private static List<Integer> filterAllOneBitIndexes(boolean[] message) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            if (message[i])
                indexes.add(i+1);

        }

        return indexes;

    }


    private static List<Integer> filterOneBitIndexesWithOneOnPosition(boolean[] message, int oneBitPositionNumber) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < message.length; i++) {
            if (message[i] && hasOneBitOnPosition(i+1, oneBitPositionNumber))
                indexes.add(i+1);

        }

        return indexes;
    }
}
