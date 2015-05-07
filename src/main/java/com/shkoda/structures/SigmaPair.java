package com.shkoda.structures;

import com.shkoda.utils.Formatter;

import static com.shkoda.utils.Formatter.toBinaryString;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaPair {
    public final int sigma0, sigma1, value;

    public SigmaPair(int value) {
        this.value = value;
        int bitNumber = Integer.bitCount(value);
        sigma0 = (bitNumber % 3) % 2;
        sigma1 = (bitNumber % 3) >> 1;
    }

    @Override
    public String toString() {
        return String.format("{s1=%d s0=%d | %d (%s)}",
                sigma1,
                sigma0,
                value,
                toBinaryString(value));
    }

    public static void main(String[] args) {
        System.out.println(new SigmaPair(16));
    }
//    @Override
//    public String toString() {
//        return String.format("%d %d %d", sigma0, sigma1, value);
//    }
}
