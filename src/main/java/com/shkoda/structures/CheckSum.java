package com.shkoda.structures;

import com.shkoda.sum.CheckSumCounter;
import com.shkoda.utils.Formatter;
import com.shkoda.utils.MathUtils;

import javax.swing.text.html.CSS;
import java.util.ArrayList;
import java.util.List;

import static com.shkoda.sum.CheckSumCounter.*;
import static com.shkoda.utils.MathUtils.bitNumber;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class CheckSum {
    private int oneBitIndexesXor;
    private List<PositionPair> oneBitOnPositionXor = new ArrayList<>();

    private int bitNumber;

    public static CheckSum build(boolean[] message) {
        int bitNumber = bitNumber(message.length);

        CheckSum sum = new CheckSum();
        sum.bitNumber = bitNumber;
        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        sum.setOneBitIndexesXor(xor(oneBitIndexes));

        for (int i = 0; i < bitNumber; i++) {
            int xor = xor(filterOneBitIndexesWithOneOnPosition(message, i));
            sum.addOneBitOnPositionXor(new PositionPair(i, xor));
        }
        return sum;
    }

    private CheckSum() {

    }

    public CheckSum addOneBitOnPositionXor(PositionPair pair) {
        this.oneBitOnPositionXor.add(pair);
        return this;
    }

    public CheckSum setOneBitIndexesXor(int oneBitIndexesXor) {
        this.oneBitIndexesXor = oneBitIndexesXor;
        return this;
    }

    public static CheckSum delta(CheckSum first, CheckSum second) {
        CheckSum delta = new CheckSum();
        delta.bitNumber = first.bitNumber;
        delta.setOneBitIndexesXor(first.oneBitIndexesXor ^ second.oneBitIndexesXor);
        int size = first.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            PositionPair xor = PositionPair.xor(first.oneBitOnPositionXor.get(i), second.oneBitOnPositionXor.get(i));
            delta.addOneBitOnPositionXor(xor);
        }
        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ");

        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i.value, Formatter.toBinaryString(i.value, bitNumber))));

        sb.append("}");
        return sb.toString();
    }
}
