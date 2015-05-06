package com.shkoda.utils;

import com.shkoda.generator.IndexGenerator;
import com.shkoda.structures.Indexes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 06.05.2015.
 */
@Deprecated
public class EquationSystemSolver {

    public static void main(String[] args) {
        Set<Indexes> indexes = IndexGenerator.generateNumbersWithDifferenceInTwoBits(1, 1024);
        List<Indexes> collect = indexes.stream()
                .filter(index -> index.countDividable(3) == 2)
                .filter(index -> index.sumDividable(3) % 3 == 1)
                .collect(Collectors.toList());

        collect.forEach(i-> System.out.println(String.format("%s %s", i, Formatter.toBinaryString(i.get()))));


//        for (IndexGenerator.Indexes index : collect) {
//            int xPosition = -1, yPosition = -1;
//            int[] ints = index.get();
//            for (int i = 2; i < ints.length; i++) {
//                if (ints[i] != 0) {
//                    if (xPosition == -1)
//                        xPosition = i;
//                    else
//                        yPosition = i;
//                }
//            }
//
//            IndexGenerator.Indexes solution = solveForModuloOne(xPosition, yPosition);
//
//            if (!Arrays.equals(index.get(), solution.get())){
//                System.out.println(String.format("%s --> %s", Arrays.toString(index.get()), Arrays.toString(solution.get())));
//            }
//        }
    }

    /**
     * индексы средние на 3 делятся, крайние - нет
     *
     * @param xPosition const bit
     * @param yPosition const bit
     */
    public static Indexes solveForModuloOne(int xPosition, int yPosition) {
        int x = 0, y = 1;
        Indexes indexes = build(x, xPosition, y, yPosition);
        int[] arr = indexes.get();

        if (arr[1] % 3 == 0
                && arr[2] % 3 == 0
                && arr[0] % 3 != 0
                && arr[3] % 3 != 0)

            return indexes;
        x = 1;
        y = 0;
        return build(x, xPosition, y, yPosition);

    }

    private static Indexes build(int x, int xPosition, int y, int yPosition) {

        int firstVarPosition = -1, secondVarPosition = -1;
        for (int i = 0; i < 4; i++) {
            if (i != xPosition && i != yPosition) {
                if (firstVarPosition == -1)
                    firstVarPosition = i;
                else
                    secondVarPosition = i;
            }
        }
        int fixedPart = (x << xPosition) + (y << yPosition);
        int i1 = (fixedPart + (1 << firstVarPosition)) % 3;
        int i2 = fixedPart + (1 << secondVarPosition);
        int i3 = fixedPart;
        int i4 = fixedPart + ((1 << firstVarPosition) + (1 << secondVarPosition));
        return new Indexes()
                .add(i1)
                .add(i2)
                .add(i3)
                .add(i4)
                .sort();
    }

    private static boolean isValid(int xPosition, int xValue, int yPosition, int yValue) {
        int firstVarPosition = -1, secondVarPosition = -1;

        for (int i = 0; i < 4; i++) {
            if (i != xPosition && i != yPosition) {
                if (firstVarPosition == -1)
                    firstVarPosition = i;
                else
                    secondVarPosition = i;
            }
        }

        int fixedPart = (xValue << xPosition) + (yValue << yPosition);
        return (fixedPart + (1 << firstVarPosition)) % 3 == 0
                && (fixedPart + (1 << secondVarPosition)) % 3 == 0
                && (fixedPart) % 3 != 0
                && (fixedPart + ((1 << firstVarPosition) + (1 << secondVarPosition))) % 3 != 0;

    }
}
