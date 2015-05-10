package com.shkoda.generator;

import com.shkoda.structures.Indexes;
import com.shkoda.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Nightingale on 26.04.2015.
 */
public class IndexGenerator {
    public static void main(String[] args) {
        Set<Indexes> indexes = generateNumbersWithDifferenceInTwoBits(1, 32);
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

    public static List<int[]> generateAllQuadraErrors(int maxIndex) {
        List<int[]> result = new ArrayList<>();
        for (int a = 1; a < maxIndex - 3; a++) {
            for (int b = a + 1; b < maxIndex - 2; b++) {
                for (int c = b + 1; c < maxIndex - 1; c++) {
                    for (int d = c + 1; d < maxIndex - 3; d++) {
                        result.add(new int[]{a,b,c,d});
                    }
                }
            }
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

}
