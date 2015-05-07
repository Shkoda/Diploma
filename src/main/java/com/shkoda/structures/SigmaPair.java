package com.shkoda.structures;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaPair {
    public final int sigma0, sigma1, value;

    public SigmaPair(int value) {
        this.value = value;
        int bitNumber = Integer.bitCount(value);
        sigma0 = bitNumber % 3;
        sigma1 = (bitNumber / 3) % 3;
    }

    @Override
    public String toString() {
        return String.format("%d %d %d", sigma0, sigma1, value);
    }
}
