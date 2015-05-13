package com.shkoda.corrector.equations;


import com.shkoda.Config;

/**
 * Created with IntelliJ IDEA.
 * User: Olga GluShko
 * Date: 10.05.13
 * Time: 11:32
 * To change this template use File | Settings | File Templates.
 */
public class MatrixCreator {

    public static int[][] createEquationMatrix(int arg1, int arg2, int arg3, int sizeOfMatrix, int firstOffset, int secondOffset) {
        int[][] matrix = new int[sizeOfMatrix][sizeOfMatrix];

        for (int i = 0; i < firstOffset; i++) {
            int current = arg1 << i;
            for (int j = 0; j < sizeOfMatrix; j++) {
                matrix[i][sizeOfMatrix - j - 1] = current % 2;
                current /= 2;
            }
        }

        for (int i = 0; i < secondOffset; i++) {
            int current = arg2 << i;
            for (int j = 0; j < sizeOfMatrix; j++) {
                matrix[i + firstOffset][sizeOfMatrix - j - 1] = current % 2;
                current /= 2;
            }
        }

        for (int j = 0; j < sizeOfMatrix; j++) {
            matrix[sizeOfMatrix - 1][sizeOfMatrix - j - 1] = arg3 % 2;
            arg3 /= 2;
        }

        return matrix;

    }

    public static int[][] createEquationMatrix(int deltaEvenCounter,
                                               int cmRec, int cmSen,
                                               int symbolIndexLength, int controlSumXOR) {

        int[][] matrix;

        switch (deltaEvenCounter) {

            //1->0, 1->0
            case 2:
                matrix = MatrixCreator.createEquationMatrix(cmRec, Config.POLYNOMIAL_MAP.get(symbolIndexLength), cmSen,
                        4 * symbolIndexLength - 2, 2 * symbolIndexLength - 1, 2 * symbolIndexLength - 2);
                break;

            //0->1, 0->1
            case -2:
                matrix = MatrixCreator.createEquationMatrix(cmSen, Config.POLYNOMIAL_MAP.get(symbolIndexLength), cmRec,
                        4 * symbolIndexLength - 2, 2 * symbolIndexLength - 1, 2 * symbolIndexLength - 2);
                break;

            //1->0, 0->1
            case 0:
                matrix = MatrixCreator.createEquationMatrix(controlSumXOR, Config.POLYNOMIAL_MAP.get(symbolIndexLength), cmSen ^ cmRec,
                        3 * symbolIndexLength, 2 * symbolIndexLength, symbolIndexLength - 1);
                break;


            default:
                throw new IllegalArgumentException("Illegal argument: delta even counter = " + deltaEvenCounter);
        }

        return matrix;
    }
}
