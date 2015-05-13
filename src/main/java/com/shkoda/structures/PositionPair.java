package com.shkoda.structures;

/**
 * Created by Nightingale on 06.05.2015.
 */
@Deprecated
public class PositionPair {
    public final int position, value;

    public PositionPair(int position, int value) {
        this.position = position;
        this.value = value;
    }

    public static PositionPair xor(PositionPair first, PositionPair second){
        return new PositionPair(first.position, first.value ^ second.value);
    }

    @Override
    public String toString() {
        return "{" +
                "position=" + position +
                ", value=" + value +
                '}';
    }
}
