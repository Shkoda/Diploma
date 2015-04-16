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

        //-----------
        int j = delta.length - 1;   //1
        int p, g, q, l;

        //2
        while (delta[j] == delta[0] || delta[j] == 0) {
            j--;
        }

        //3
        if (delta[j] != 1) {
            //4
            g = delta[j] ^ delta[0];
            l = j;
            //5
            do {
                j--;
            }
            while (delta[j] == delta[0] || delta[j] == delta[1] || delta[j] == 0);

            //6
            if (delta[j] != delta[0] && delta[j] != delta[l] && delta[j] != 0) {
                if ((g & (1 << (j - 1))) >> (j - 1) == 1) {
                    q = delta[0] ^ delta[l] ^ delta[j];
                    p = delta[0] ^ delta[j];
                    return new Result(g, p, q);
                } else {
                    //7
                    q = delta[j];
                    p = delta[l] ^ delta[j];
                    return new Result(g, p, q);
                }
            }

        }
        //8
        q = delta[j];
        l = j;
        //9
        do {
            j--;
        }
        while (delta[j] == delta[0] ||
                delta[j] == delta[l] ||
                delta[j] == (delta[0] ^ delta[l]) ||
                delta[j] == 0);

        //10
        if (delta[j] != delta[0] ||
                delta[j] != delta[l] ||
                delta[j] != (delta[0] ^ delta[l]) ||
                delta[j] != 0) {
            if (((q & (1 << (j - 1))) >> (j - 1)) == 1) {
                p = delta[j] ^ delta[l];
                g = delta[0] ^ delta[j];
                return new Result(g, p, q);
            }

        }

        p = delta[j];
        g = delta[0] ^ delta[l] ^ delta[j];
        return new Result(g, p, q);
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
