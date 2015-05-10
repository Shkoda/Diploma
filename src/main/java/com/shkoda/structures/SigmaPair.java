package com.shkoda.structures;

import static com.shkoda.utils.Formatter.toBinaryString;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaPair {
    public final int sigma3_0;
    public final int sigma3_1;

    public final int sigma4_0;
    public final int sigma4_1;

    public final int sigma5_0;
    public final int sigma5_1;
    public final int sigma5_2;


    public final int value;

    public SigmaPair(int value) {
        this.value = value;
        int bitNumber = Integer.bitCount(value);

        sigma3_0 = (bitNumber % 3) % 2;
        sigma3_1 = (bitNumber % 3) >> 1;

        sigma4_0 = (bitNumber % 4) % 2;
        sigma4_1 = (bitNumber % 4) >> 1;

        sigma5_0 = (bitNumber % 5) % 2;
        sigma5_1 = ((bitNumber % 5) >> 1) & 1;
        sigma5_2 = (bitNumber % 5) >> 2;
    }

    @Override
    public String toString() {
        return String.format("{s3_1=%d s3_0=%d : s4_1=%d s4_0=%d :  s5_2=%d s5_1=%d s5_0=%d :  | %d (%s)}",
                sigma3_1,
                sigma3_0,

                sigma4_1,
                sigma4_0,

                sigma5_2,
                sigma5_1,
                sigma5_0,


                value,
                toBinaryString(value));
    }

    public static void main(String[] args) {
        System.out.println(new SigmaPair(16));
    }
//    @Override
//    public String toString() {
//        return String.format("%d %d %d", sigma3_0, sigma3_1, value);
//    }
}
