package com.shkoda.structures.sums;

import com.shkoda.structures.SigmaPair;
import com.shkoda.utils.GaluaMath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.sum.CheckSumCounter.filterAllOneBitIndexes;
import static com.shkoda.sum.CheckSumCounter.filterOneBitIndexesWithOneOnPosition;
import static com.shkoda.utils.MathUtils.hasOneBitOnPosition;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class SigmaCheckSum extends AbstractCheckSum<SigmaCheckSum> {
    public int sigma0, sigma1;
    public int mod3, multMod3, count3;
    public List<Integer> oneBitOnPositionXor = new ArrayList<>();
    public List<Integer> triple = new ArrayList<>();

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

        sigma0 = xor(sigmaPairs.stream()
                .filter(sigma -> sigma.sigma0 != 0)
                .map(sigma -> sigma.value)
                .collect(Collectors.toList()));

        sigma1 = xor(sigmaPairs.stream()
                .filter(sigma -> sigma.sigma1 != 0)
                .map(sigma -> sigma.value)
                .collect(Collectors.toList()));

        List<Integer> zeroMod3 = oneBitIndexes.stream()
                .filter(i -> i % 3 == 0)
                .collect(Collectors.toList());

        this.count3 = zeroMod3.size();
        this.mod3 = xor(zeroMod3);
        this.multMod3 = GaluaMath.multipleArrayInField(zeroMod3, 299);

        for (int i = 0; i < bitNumber; i++) {
            int pos = i ;
            List<Integer> triBits = zeroMod3.stream().filter(z -> hasOneBitOnPosition(z, pos)).collect(Collectors.toList());
            int xor = xor(triBits);
            this.triple.add(xor);
        }

    }

    public static boolean equals(SigmaCheckSum first, SigmaCheckSum second) {
        if (first.oneBitIndexesXor != second.oneBitIndexesXor) return false;
        for (int i = 0; i < first.oneBitOnPositionXor.size(); i++) {
            if (first.oneBitOnPositionXor.get(i).intValue() != second.oneBitOnPositionXor.get(i).intValue())
                return false;
        }
        return true;

    }

    public SigmaCheckSum() {
        super();
    }

    public SigmaCheckSum delta(SigmaCheckSum other) {
        SigmaCheckSum delta = new SigmaCheckSum();
        delta.bitNumber = this.bitNumber;
        delta.oneBitIndexesXor = this.oneBitIndexesXor ^ other.oneBitIndexesXor;
        delta.sigma0 = this.sigma0 ^ other.sigma0;
        delta.sigma1 = this.sigma1 ^ other.sigma1;


        delta.mod3 = this.mod3 ^ other.mod3;
        delta.multMod3 = this.multMod3 ^ other.multMod3;
        delta.count3 = other.count3 - this.count3;

        int size = this.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            delta.oneBitOnPositionXor.add(this.oneBitOnPositionXor.get(i) ^ other.oneBitOnPositionXor.get(i));
        }

        for (int i = 0; i < size; i++) {
            delta.triple.add(this.triple.get(i) ^ other.triple.get(i));
        }
        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ")
                .append(sigma0)
                .append(" ").append(sigma1)
                .append(" | ")

                .append(count3)
                .append(" ").append(mod3).append(" ").append(multMod3)
                .append(" | ");
        ;

        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append(" || ");
        triple.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append("}");
        return sb.toString();
    }
}
