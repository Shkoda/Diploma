package com.shkoda.utils;

import java.util.List;

public class GaluaMath {

    public static void main(String[] args) {
        // [3, 5, 10, 12]
        //228
        int a = multiple(9, 144);
//        int b = modulo(5,5);
        System.out.println(a);

    }


    public static int multipleArrayInField(List<Integer> arguments, int polynom) {
        int result = 0;

        for (Integer argument : arguments) {
            if (result == 0)
                result = 1;
            result = multiple(result, argument);
            result = modulo(result, polynom);
        }
        return result;
    }

    public static int xorArray(int[] arguments) {
        int result = 0;
        for (int i = 0; i < arguments.length; i++)
            result ^= multiple(arguments[i], (i + 1));
        return result;
    }

    public static int multiple(int arg1, int arg2) {
        int result = 0;
        int weight = 1;

        while (arg2 > 0) {
            result ^= arg1 * (arg2 % 2 * weight);
            arg2 /= 2;
            weight *= 2;
        }
        return result;

    }

    public static int multipleInField(int arg1, int arg2, int polynom) {
        int result = multiple(arg1, arg2);
        result = modulo(result, polynom);
        return result;
    }

    public static long multiple(long arg1, long arg2) {
        long result = 0;
        long weight = 1;

        while (arg2 > 0) {
            result ^= arg1 * (arg2 % 2 * weight);
            arg2 /= 2;
            weight *= 2;
        }
        return result;

    }

    public static long multArrays(int[] arguments) {
        long result = 1;
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] != 0)
                result = multiple(result, arguments[i] * (i + 1));
        }
        return result;

    }

    public static int length(long arg) {
        return (int) Math.ceil(Math.log(arg + 1) / Math.log(2));
    }

    public static int divide(int dividend, int divider) {
        int result = 0;

        int dividerLength = length(divider);

        int dividendLength = length(dividend);

        int offset = dividendLength - dividerLength;
        int temp;

        while ((dividend != 0) && (offset >= 0)) {
            result += Math.pow(2, offset);
            temp = dividend ^ (int) (divider * Math.pow(2, offset));
            if (temp != 0)
                offset -= length(dividend) - length(temp);
            dividend = temp;
        }
        return result;


    }

    public static long divide(long dividend, long divider) {
        long result = 0;

        int dividerLength = length(divider);

        int dividendLength = length(dividend);

        int offset = dividendLength - dividerLength;
        long temp;

        while ((dividend != 0) && (offset >= 0)) {
            result += Math.pow(2, offset);
            temp = dividend ^ (int) (divider * Math.pow(2, offset));
            if (temp != 0)
                offset -= length(dividend) - length(temp);
            dividend = temp;
        }
        return result;


    }

    public static int modulo(long dividend, long divider) {

        int dividerLength = length(divider);

        int dividendLength = length(dividend);

        int offset = dividendLength - dividerLength;
        long temp;

        //  while (offset > 0) {
        while (dividend > divider) {
            temp = dividend ^ (int) (divider * Math.pow(2, offset));
            //   temp =  (divider << offset);

            //      temp ^= dividend;
            if (temp != 0)
                offset -= length(dividend) - length(temp);
            dividend = temp;
        }
        return (int) dividend;
    }

    public static void testDistribution(int[] arg1, int[] arg2) {
        for (int i = 0; i < arg1.length; i++) {
            System.out.print(GaluaMath.multiple((arg1[i] ^ arg2[i]), arg1[i]) + " = ");
            System.out.println(GaluaMath.multiple(arg1[i], arg1[i]) ^ GaluaMath.multiple(arg1[i], arg2[i]));
        }


    }

    public static void testDistr() {
        for (int i = 1; i < 600; i++) {
            for (int j = 1; j < 600; j++) {
                int t1 = GaluaMath.multiple((i ^ j), i);
                int t2 = GaluaMath.multiple(i, i) ^ GaluaMath.multiple(i, j);
                if (t1 != t2)
                    System.out.println("ooops: " + i + " " + j);
            }
        }
        System.out.println("OK");
    }

//    public static void main(String[] args) {
//        primes();
//        //   System.out.println(multiple(16,17));
//    }

    public static void primes() {
        int min = 1030;
        int max = 2000;
        int[] S = new int[max];


        for (int i = min; i < max; i++)
            S[i] = 1;

        S[1] = 0; // 1 - не простое число

        for (int i = min; i < max; i++) {

            // перебираем возможные делители от 2 до sqrt(n)
            for (int d = 2; d * d < max; d++) {
                // если разделилось нацело, то составное
                if (modulo(i, d) == 0)
                    S[i] = 0;
            }

        }

        for (int i = max - 1; i > min; i--) {
            if (S[i] == 1) {
                System.out.println(i);
                // return;
            }


        }
    }
}
