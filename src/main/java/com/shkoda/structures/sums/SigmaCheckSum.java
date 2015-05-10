package com.shkoda.structures.sums;

import com.shkoda.structures.SigmaPair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.shkoda.sum.CheckSumCounter.filterAllOneBitIndexes;
import static com.shkoda.sum.CheckSumCounter.filterOneBitIndexesWithOneOnPosition;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class SigmaCheckSum extends AbstractCheckSum<SigmaCheckSum> {
    public int sigma3_0, sigma3_1;
    public int sigma4_0, sigma4_1;
    public int sigma5_0, sigma5_1, sigma5_2;
    public List<Integer> oneBitOnPositionXor = new ArrayList<>();

    public SigmaCheckSum(boolean[] message) {
        super(message);
        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        oneBitIndexesXor = xor(oneBitIndexes);

        for (int i = 0; i < bitNumber; i++) {
            int xor = xor(filterOneBitIndexesWithOneOnPosition(message, i));
            this.oneBitOnPositionXor.add(xor);
        }

//        List<Integer> allIndexes = allIndexes(message);

        List<SigmaPair> sigmaPairs = oneBitIndexes.stream()
                .map(SigmaPair::new)
                .collect(Collectors.toList());

//        sigmaPairs.forEach(System.out::println);

        sigma3_0 = sigma(sigmaPairs, sigma -> sigma.sigma3_0 != 0);
        sigma3_1 = sigma(sigmaPairs, sigma -> sigma.sigma3_1 != 0);

        sigma4_0 = sigma(sigmaPairs, sigma -> sigma.sigma4_0 != 0);
        sigma4_1 = sigma(sigmaPairs, sigma -> sigma.sigma4_1 != 0);

        sigma5_0 = sigma(sigmaPairs, sigma -> sigma.sigma5_0 != 0);
        sigma5_1 = sigma(sigmaPairs, sigma -> sigma.sigma5_1 != 0);
        sigma5_2 = sigma(sigmaPairs, sigma -> sigma.sigma5_2 != 0);

    }

    private int sigma(List<SigmaPair> sigmaPairs, Predicate<SigmaPair> predicate) {
        return xor(sigmaPairs.stream()
                .filter(predicate::test)
                .map(sigma -> sigma.value)
                .collect(Collectors.toList()));
    }

    public static boolean equals(SigmaCheckSum first, SigmaCheckSum second) {
        if (first.oneBitIndexesXor != second.oneBitIndexesXor) return false;
        for (int i = 0; i < first.oneBitOnPositionXor.size(); i++) {
            if (first.oneBitOnPositionXor.get(i).intValue() != second.oneBitOnPositionXor.get(i).intValue())
                return false;
        }
        return true;
    }

    private SigmaCheckSum() {
        super();
    }

    public SigmaCheckSum delta(SigmaCheckSum other) {
        SigmaCheckSum delta = new SigmaCheckSum();
        delta.bitNumber = this.bitNumber;
        delta.oneBitIndexesXor = this.oneBitIndexesXor ^ other.oneBitIndexesXor;
        delta.sigma3_0 = this.sigma3_0 ^ other.sigma3_0;
        delta.sigma3_1 = this.sigma3_1 ^ other.sigma3_1;

        delta.sigma4_0 = this.sigma4_0 ^ other.sigma4_0;
        delta.sigma4_1 = this.sigma4_1 ^ other.sigma4_1;

        delta.sigma5_0 = this.sigma5_0 ^ other.sigma5_0;
        delta.sigma5_1 = this.sigma5_1 ^ other.sigma5_1;
        delta.sigma5_2 = this.sigma5_2 ^ other.sigma5_2;
        int size = this.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            delta.oneBitOnPositionXor.add(this.oneBitOnPositionXor.get(i) ^ other.oneBitOnPositionXor.get(i));
        }
        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ")
                .append(sigma3_0)
                .append(" ").append(sigma3_1)
                .append(" : ").append(sigma4_0)
                .append(" ").append(sigma4_1)
                .append(" : ").append(sigma5_0)
                .append(" ").append(sigma5_1)
                .append(" ").append(sigma5_2)
                .append(" | ");

        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append("}");
        return sb.toString();
    }
}
