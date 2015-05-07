package com.shkoda.structures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class Indexes implements Comparable<Indexes> {
    private int pointer = 0;
    private int[] arr = new int[4];

    public Indexes add(int index) {
        arr[pointer++] = index;
        return this;
    }

    public boolean containsAnyDividable(int byValue) {
        return countDividable(byValue) == 0;
    }

    public List<Integer> dividable(int byValue) {
        List<Integer> res = new ArrayList<>();
        for (int i : arr) {
            if (i % byValue == 0) res.add(i);
        }
        return res;
    }

    public int sumDividable(int byValue) {
        int res = 0;
        for (int i : arr) {
            if (i % byValue == 0) res ^= i;
        }
        return res;
    }

    public int countDividable(int byValue) {
        int counter = 0;
        for (int i : arr) {
            if (i % byValue == 0) counter++;
        }
        return counter;
    }

    public int[] get() {
        return arr;
    }

    public boolean inRange(int min, int max) {
        sort();
        return !(arr[0] < min || arr[arr.length - 1] > max);
    }

    public Indexes sort() {
        Arrays.sort(arr);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indexes indexes = (Indexes) o;
        return Arrays.equals(arr, indexes.arr);

    }

    @Override
    public int hashCode() {
        return arr != null ? Arrays.hashCode(arr) : 0;
    }

    @Override
    public int compareTo(Indexes that) {
        if (that == null)
            return 1;
        for (int i = 0; i < arr.length; i++) {
            if (this.arr[i] != that.arr[i])
                return Integer.compare(this.arr[i], that.arr[i]);
        }

        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}
