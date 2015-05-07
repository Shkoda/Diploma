package com.shkoda.structures.sums;

import com.shkoda.structures.PositionPair;

import java.util.ArrayList;
import java.util.List;

import static com.shkoda.utils.MathUtils.bitNumber;
import static com.shkoda.utils.MathUtils.xor;

/**
 * Created by Nightingale on 07.05.2015.
 */
public abstract class AbstractCheckSum<T extends AbstractCheckSum> {
    protected int bitNumber;
    protected int oneBitIndexesXor;
    protected List<PositionPair> oneBitOnPositionXor = new ArrayList<>();

    public AbstractCheckSum(boolean[] message) {
        bitNumber = bitNumber(message.length);
    }

    protected AbstractCheckSum() {
    }

    public abstract T addOneBitOnPositionXor(PositionPair pair);

    public abstract T setOneBitIndexesXor(int oneBitIndexesXor);

    public abstract T delta(T other);
}