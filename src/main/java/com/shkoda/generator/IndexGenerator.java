package com.shkoda.generator;

import com.shkoda.utils.MathUtils;

import java.util.*;

/**
 * Created by Nightingale on 26.04.2015.
 */
public class IndexGenerator {
    public static void main(String[] args) {
        Set<Indexes> indexes = generateNumbersWithDifferenceInTwoBits(1, 15);
        indexes.forEach(System.out::println);
    }

    public static Set<Indexes> generateNumbersWithDifferenceInTwoBits(int minIndex, int maxIndex, int lowPosition, int highPosition) {
        Set<Indexes> result = new TreeSet<>();

        for (int value = minIndex; value <= maxIndex; value++) {

            int high = 1 << highPosition;
            int low = 1 << lowPosition;

            int mask = ~(high + low);
            int masked = value & mask;

            Indexes indexes = new Indexes();
            indexes.add(masked)
                    .add(masked + low)
                    .add(masked + high)
                    .add(masked + low + high)
                    .sort();
            if (indexes.inRange(minIndex, maxIndex))
                result.add(indexes);

        }
        return result;
    }


    public static Set<Indexes> generateNumbersWithDifferenceInTwoBits(int minIndex, int maxIndex) {
        Set<Indexes> result = new TreeSet<>();
        int bitNumber = MathUtils.bitNumber(maxIndex);

        for (int highPosition = 1; highPosition < bitNumber; highPosition++) {
            for (int lowPosition = highPosition - 1; lowPosition >= 0; lowPosition--) {

                Set<Indexes> indexes = generateNumbersWithDifferenceInTwoBits(minIndex, maxIndex, lowPosition, highPosition);
                result.addAll(indexes);
            }
        }

        return result;

    }

    public static class Indexes implements Comparable<Indexes> {
        private int pointer = 0;
        private int[] arr = new int[4];

        public Indexes add(int index) {
            arr[pointer++] = index;
            return this;
        }

        public boolean inRange(int min, int max) {
            sort();
            return !(arr[0] < min || arr[arr.length - 1] > max);
        }

        public void sort() {
            Arrays.sort(arr);
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
}
