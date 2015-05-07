package com.shkoda.structures.sums;

import com.shkoda.structures.PositionPair;
import com.shkoda.utils.Formatter;

import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.sum.CheckSumCounter.*;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class CheckSum extends AbstractCheckSum<CheckSum> {

    private int dividableByThreeIndexXor;

    private int countDividableByThree;

    public CheckSum(boolean[] message) {
        super(message);
        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        oneBitIndexesXor = xor(oneBitIndexes);

        List<Integer> dividableByThree = oneBitIndexes.stream()
                .filter(i -> i % 3 == 0)
                .collect(Collectors.toList());
        dividableByThreeIndexXor = xor(dividableByThree);
        countDividableByThree = dividableByThree.size();

        for (int i = 0; i < bitNumber; i++) {
            int xor = xor(filterOneBitIndexesWithOneOnPosition(message, i));
            addOneBitOnPositionXor(new PositionPair(i, xor));
        }
    }

    private CheckSum(){
        super();
    }



    @Override
    public CheckSum addOneBitOnPositionXor(PositionPair pair) {
        this.oneBitOnPositionXor.add(pair);
        return this;
    }

    @Override
    public CheckSum setOneBitIndexesXor(int oneBitIndexesXor) {
        this.oneBitIndexesXor = oneBitIndexesXor;
        return this;
    }

    public CheckSum delta(CheckSum other) {
        CheckSum delta = new CheckSum();
        delta.bitNumber = this.bitNumber;
        delta.setOneBitIndexesXor(this.oneBitIndexesXor ^ other.oneBitIndexesXor);
        delta.dividableByThreeIndexXor = this.dividableByThreeIndexXor ^ other.dividableByThreeIndexXor;
        delta.countDividableByThree = other.countDividableByThree - this.countDividableByThree;
        int size = this.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            PositionPair xor = PositionPair.xor(this.oneBitOnPositionXor.get(i), other.oneBitOnPositionXor.get(i));
            delta.addOneBitOnPositionXor(xor);
        }
        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ")
                .append(dividableByThreeIndexXor)
                .append(" ").append(countDividableByThree)
                .append(" | ");

        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i.value, Formatter.toBinaryString(i.value, bitNumber))));

        sb.append("}");
        return sb.toString();
    }
}
