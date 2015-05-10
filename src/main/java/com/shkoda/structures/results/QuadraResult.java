package com.shkoda.structures.results;

import com.shkoda.generator.MessageGenerator;

import java.util.Arrays;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class QuadraResult {
    public final int a,b,c,d;

    private String log;
    private final int[] ints;


    public QuadraResult(int a, int b, int c, int d) {
        ints = new int[]{a, b, c, d};
        Arrays.sort(ints);
        this.a = ints[0];
        this.b = ints[1];
        this.c = ints[2];
        this.d = ints[3];
    }

    public boolean[] generateFixedMessage(boolean[] receivedMessage){
       return MessageGenerator.invertBits(receivedMessage, ints);
    }

    public String getLog() {
        return log;
    }

    public QuadraResult setLog(String log) {
        this.log = log;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
