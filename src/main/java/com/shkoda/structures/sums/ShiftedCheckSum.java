package com.shkoda.structures.sums;

import com.shkoda.structures.PositionPair;
import com.shkoda.utils.Formatter;

import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.sum.CheckSumCounter.filterAllOneBitIndexes;
import static com.shkoda.sum.CheckSumCounter.filterOneBitIndexesWithOneOnPosition;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class ShiftedCheckSum extends AbstractCheckSum<ShiftedCheckSum> {

    private ShiftedCheckSum() {
        super();
    }

    public ShiftedCheckSum(boolean[] message) {
        super(message);
        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        oneBitIndexesXor = xor(shifted(oneBitIndexes, bitNumber));

        for (int i = 0; i < bitNumber; i++) {
            List<Integer> indexes = filterOneBitIndexesWithOneOnPosition(message, i);
            int xor = xor(shifted(indexes, bitNumber));
            this.oneBitOnPositionXor.add(new PositionPair(i, xor));
        }
    }

    private static List<Integer> shifted(List<Integer> indexes, int bitNumber) {
        return indexes.stream()
                .map(index -> index << (index % bitNumber))
                .collect(Collectors.toList());
    }

    public ShiftedCheckSum delta(ShiftedCheckSum other) {
        ShiftedCheckSum delta = new ShiftedCheckSum();
        delta.bitNumber = this.bitNumber;
        delta.oneBitIndexesXor = this.oneBitIndexesXor ^ other.oneBitIndexesXor;

        int size = this.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            PositionPair xor = PositionPair.xor(this.oneBitOnPositionXor.get(i), other.oneBitOnPositionXor.get(i));
            delta.oneBitOnPositionXor.add(xor);
        }
        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ")

                .append(" | ");

        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i.value, Formatter.toBinaryString(i.value, bitNumber))));

        sb.append("}");
        return sb.toString();
    }
}
