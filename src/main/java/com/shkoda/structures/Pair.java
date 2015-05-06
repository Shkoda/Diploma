package com.shkoda.structures;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class Pair {
    public final int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
