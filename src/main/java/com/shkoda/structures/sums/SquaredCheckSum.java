package com.shkoda.structures.sums;

import com.shkoda.Config;
import com.shkoda.structures.SigmaPair;
import com.shkoda.utils.GaluaMath;
import com.shkoda.utils.ModuleIntervalMath;

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
public class SquaredCheckSum extends AbstractCheckSum<SquaredCheckSum> {

    public List<Integer> oneBitOnPositionXor = new ArrayList<>();
    public List<Integer> multOneBitOnPositionXor = new ArrayList<>();
    public List<Integer> eos = new ArrayList<>();

    public SquaredCheckSum(boolean[] message) {
        super(message);
        List<Integer> oneBitIndexes = filterAllOneBitIndexes(message);
        oneBitIndexesXor = xor(oneBitIndexes);
        for (int i = 0; i < bitNumber; i++) {
            List<Integer> indexes = filterOneBitIndexesWithOneOnPosition(message, i);
            int xor = xor(indexes);
            this.oneBitOnPositionXor.add(xor);
            this.multOneBitOnPositionXor.add(GaluaMath.multipleArrayInField(indexes, Config.POLYNOMIAL_MAP.get(bitNumber)));
            this.eos.add(indexes.size() % 8);
        }
    }

    public static boolean equals(SquaredCheckSum first, SquaredCheckSum second) {
        if (first.oneBitIndexesXor != second.oneBitIndexesXor) return false;
        for (int i = 0; i < first.oneBitOnPositionXor.size(); i++) {
            if (first.oneBitOnPositionXor.get(i).intValue() != second.oneBitOnPositionXor.get(i).intValue())
                return false;
        }
        return true;
    }

    public SquaredCheckSum() {
        super();
    }

    public SquaredCheckSum delta(SquaredCheckSum other) {
        SquaredCheckSum delta = new SquaredCheckSum();
        delta.bitNumber = this.bitNumber;
        delta.oneBitIndexesXor = this.oneBitIndexesXor ^ other.oneBitIndexesXor;

        int size = this.oneBitOnPositionXor.size();
        for (int i = 0; i < size; i++) {
            delta.oneBitOnPositionXor.add(this.oneBitOnPositionXor.get(i) ^ other.oneBitOnPositionXor.get(i));
            delta.multOneBitOnPositionXor.add(this.multOneBitOnPositionXor.get(i) ^ other.multOneBitOnPositionXor.get(i));
            delta.eos.add(ModuleIntervalMath.deltaEoS(other.eos.get(i), this.eos.get(i)));
//            delta.eos.add(ModuleIntervalMath.deltaEoS( this.eos.get(i), other.eos.get(i)));
        }


        return delta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(oneBitIndexesXor).append(" | ");
        oneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append(" | ");
        multOneBitOnPositionXor.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append(" | ");
        eos.forEach(i -> sb.append(String.format("%d ", i)));
        sb.append("}");
        return sb.toString();
    }
}
