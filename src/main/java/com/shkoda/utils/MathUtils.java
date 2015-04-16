package com.shkoda.utils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class MathUtils {
    public static int bitNumber(int value){
        return (int) ceil(log(value + 1) / log(2));
    }

    public static boolean hasOneBitOnPosition(int value, int position){
        return (value >> position) % 2  == 1;
    }

    public static int xor(List<Integer> indexes) {
        if (indexes.isEmpty()) return 0;
        int xor = indexes.get(0);
        for (int i = 1; i < indexes.size(); i++)
            xor ^= indexes.get(i);
        return xor;

    }

    public static List<Integer> xor(List<Integer> first, List<Integer> second) {
        List<Integer> xor = new ArrayList<>();

        for (int i = 0; i < first.size(); i++) {
            xor.add(first.get(i) ^ second.get(i));
        }

        return xor;
    }


    public static boolean equalsSorted(int[] first, int[] second){
        for (int i = 0; i < first.length; i++) {
            if (first[i]!=second[i])
                return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(hasOneBitOnPosition(5, 4));
    }
}
