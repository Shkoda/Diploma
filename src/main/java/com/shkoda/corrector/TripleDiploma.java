package com.shkoda.corrector;

/**
 * Created by Nightingale on 16.04.2015.
 */
public class TripleDiploma {

    public static void main(String[] args) {
        int[] delta = new int[]{15, 9, 14, 14, 15};
        Result result = solve(delta);
        System.out.println(result);
    }

    public static Result solve(int[] delta) {
        int low, middle, high, tmp;
        //1
        int j = delta.length - 1;
        //2
        while (delta[j] == delta[0] || delta[j] == 0) j--;
        //3
        if (delta[j] != 1) {
            //4
            low = delta[j] ^ delta[0];
            tmp = j;
            //5
            do {
                j--;
            }
            while (delta[j] == delta[0] || delta[j] == delta[1] || delta[j] == 0);
            //6
            if (delta[j] != delta[0] && delta[j] != delta[tmp] && delta[j] != 0) {
                if (bitOnPosition(low, j) == 1) {
                    middle = delta[0] ^ delta[tmp] ^ delta[j];
                    high = delta[0] ^ delta[j];
                    return new Result(low, high, middle);
                } else {
                    //7
                    middle = delta[j];
                    high = delta[tmp] ^ delta[j];
                    return new Result(low, high, middle);
                }
            }
        }
        //8
        middle = delta[j];
        tmp = j;
        //9
        do {
            j--;
        }
        while (delta[j] == delta[0] ||
                delta[j] == delta[tmp] ||
                delta[j] == (delta[0] ^ delta[tmp]) ||
                delta[j] == 0);
        //10
        if (delta[j] != delta[0] &&
                delta[j] != delta[tmp] &&
                delta[j] != (delta[0] ^ delta[tmp]) &&
                delta[j] != 0) {
            if (bitOnPosition(middle, j) == 1) {
                high = delta[j] ^ delta[tmp];
                low = delta[0] ^ delta[j];
                return new Result(low, high, middle);
            }
        }
        high = delta[j];
        low = delta[0] ^ delta[tmp] ^ delta[j];
        return new Result(low, high, middle);
    }

    private static int bitOnPosition(int number, int position) {
        return (number & (1 << (position - 1))) >> (position - 1);
    }

    public static class Result {
        public final int g, q, p;

        public Result(int g, int q, int p) {
            this.g = g;
            this.q = q;
            this.p = p;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "g=" + g +
                    ", q=" + q +
                    ", p=" + p +
                    '}';
        }
    }
}
