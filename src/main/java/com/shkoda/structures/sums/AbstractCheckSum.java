package com.shkoda.structures.sums;

import static com.shkoda.utils.MathUtils.bitNumber;

/**
 * Created by Nightingale on 07.05.2015.
 */
public abstract class AbstractCheckSum<T extends AbstractCheckSum> {
    protected int bitNumber;
    public int oneBitIndexesXor;


    public AbstractCheckSum(boolean[] message) {
        bitNumber = bitNumber(message.length);
    }

    protected AbstractCheckSum() {
    }



    public abstract T delta(T other);
}
