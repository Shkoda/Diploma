package com.shkoda.structures.results;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class TripleResult {
    public final int low;
    public final int middle;
    public final int high;
    public final String log;

    public TripleResult(int low, int middle, int high, String log) {
        this.low = low;
        this.middle = middle;
        this.high = high;
        this.log = log;
    }

    public int[] toArray() {
        return new int[]{low, middle, high};
    }

    @Override
    public String toString() {
        return "Result{" +
                "low=" + low +
                ", middle=" + middle +
                ", high=" + high +
                '}';
    }
}
