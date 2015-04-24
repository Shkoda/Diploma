package com.shkoda.corrector;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.*;
import static java.lang.String.*;

/**
 * C0reated by Nightingale on 16.04.2015.
 */
public class TripleErrorCorrector {

    public static void main(String[] args) {
        //Real bad positions  ::
        int[] delta = new int[]{3, 7, 3, 1};
        Result result = solve(delta);
        System.out.println(result.log);
        System.out.println(result);
    }

    public static Result solve(int[] delta) {
        StringBuilder sb = new StringBuilder();

        int low, middle, high, tmp;
        //1
        int j = delta.length - 1;

        sb.append(format("delta = %s\n", Arrays.toString(delta)));
        sb.append(format("1. j = %d\n", j));
        //2
        while (delta[j] == delta[0] || delta[j] == 0) {
            j--;
            sb.append(format("2. j = %d\n", j));
        }
        //3
        if (bitOnPosition(delta[j], j) != 1) {
            sb.append(format("3. bit in %s on position %d != 1\n", toBinaryString(delta[j]), j));

            //4
            low = delta[j] ^ delta[0];
            tmp = j;

            sb.append(format("4. low = %s[%d] ^ %s[0] = %d, tmp = %d\n",toBinaryString(delta[j]), j, toBinaryString(delta[j]), low, tmp));
            //5
            do {
                j--;
                sb.append(format("5. j = %d\n", j));
            }
//            while (delta[j] == delta[0] || delta[j] == delta[1] || delta[j] == 0);
            while (j == 0 || j == 1 || delta[j] == 0);

            //6
            sb.append(format("6. %s[%d] = %d\n", Arrays.toString(delta), j, delta[j]));
            if (delta[j] != delta[0] && delta[j] != delta[tmp] && delta[j] != 0) {
                sb.append(format("\tbit in %s on position %d = %d\n", toBinaryString(low), j, bitOnPosition(low, j)));

                if (bitOnPosition(low, j) == 1) {
                    middle = delta[0] ^ delta[tmp] ^ delta[j];
                    high = delta[0] ^ delta[j];
                    sb.append(format("\tmiddle = %d, high = %d\n", middle, high));


                    return new Result(low, middle, high, sb.toString());
                } else {
                    //7
                    middle = delta[j];
                    high = delta[tmp] ^ delta[j];
                    sb.append(format("7. middle = %d, high = %d\n", middle, high));

                    return new Result(low, middle, high, sb.toString());
                }
            }
        }
        //8
        middle = delta[j];
        tmp = j;

        sb.append(format("8. middle = %d, tmp = %d\n", middle, tmp));
        //9
        do {
            j--;
            sb.append(format("9. j = %d\n", j));
        }
//        while (delta[j] == delta[0] ||
//                delta[j] == delta[tmp] ||
//                delta[j] == (delta[0] ^ delta[tmp]) ||
//                delta[j] == 0);
        while (j == 0 ||
                j == tmp ||
                delta[j] == (delta[0] ^ delta[tmp]) ||
                delta[j] == 0);
        //10
//        if (delta[j] != delta[0] &&
//                delta[j] != delta[tmp] &&
//                delta[j] != (delta[0] ^ delta[tmp]) &&
//                delta[j] != 0) {
        if (delta[j] != delta[0] &&
                delta[j] != delta[tmp] &&
                delta[j] != (delta[0] ^ delta[tmp]) &&
                delta[j] != 0) {
            sb.append(format("10. delta[%d] = %d\n", j, delta[j]));
            sb.append(format("\tbit in %s on position %d = %d\n", toBinaryString(middle), j, bitOnPosition(middle, j)));


            if (bitOnPosition(middle, j) == 1) {
                high = delta[j] ^ delta[tmp];
                low = delta[0] ^ delta[j];

                System.out.println(sb);
                return new Result(low, middle, high, sb.toString());
            }
        }
        high = delta[j];
        low = delta[0] ^ delta[tmp] ^ delta[j];

        return new Result(low, middle, high, sb.toString());
    }

    private static int bitOnPosition(int number, int position) {
        return (number >> (position - 1)) & 1;
    }

    public static int[] solve(List<Integer> deltaList) {
        int[] delta = new int[deltaList.size()];
        for (int i = 0; i < deltaList.size(); i++) {
            delta[i] = deltaList.get(i);
        }
        Result result = solve(delta);
        return new int[]{result.g, result.p, result.q};
    }

    public static class Result {
        public final int g;
        public final int q;
        public final int p;
        public final  String log;

        public Result(int g, int q, int p, String log) {
            this.g = g;
            this.q = q;
            this.p = p;
            this.log = log;
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
