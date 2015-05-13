package com.shkoda.corrector.equations;

import com.shkoda.Config;
import com.shkoda.structures.Pair;
import com.shkoda.utils.ModuleIntervalMath;

public class EquationSystemSolver {

    public static Pair solve(int symbolIndexLength, int controlSumXOR, int cmSen, int cmRec, int deltaEoS) {

        int[] roots;
        if (cmRec == 0 || cmSen == 0) {
            int mult = cmRec == 0 ? cmSen : cmRec;
            roots = SecondTypeEquationSolver.solve(controlSumXOR, mult, symbolIndexLength);
            return new Pair(roots[0], roots[1]);
        }


        int[][] matrix = MatrixCreator.createEquationMatrix(deltaEoS, cmRec, cmSen, symbolIndexLength, controlSumXOR);

        int sizeOfFirstRoot = (deltaEoS == 0) ? 2 * symbolIndexLength : 2 * symbolIndexLength - 1;
        int sizeOfSecondRoot = (deltaEoS == 0) ? symbolIndexLength - 1 : 2 * symbolIndexLength - 2;

        roots = FirstTypeEquationSolver.solve(matrix, sizeOfFirstRoot, sizeOfSecondRoot);

        if (deltaEoS == 0) {
            int[][] secondMatrix = MatrixCreator.createEquationMatrix(
                    roots[0],
                    Config.POLYNOMIAL_MAP.get(symbolIndexLength),
                    cmSen,
                    3 * symbolIndexLength,
                    symbolIndexLength,
                    symbolIndexLength - 1);
            roots = FirstTypeEquationSolver.solve(secondMatrix, symbolIndexLength, symbolIndexLength - 1);

        } else {
            roots = SecondTypeEquationSolver.solve(controlSumXOR, roots[0], symbolIndexLength);
        }
        return new Pair(roots[0], roots[0] ^ controlSumXOR);
    }

}
