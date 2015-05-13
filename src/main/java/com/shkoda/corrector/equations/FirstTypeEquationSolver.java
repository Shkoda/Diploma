package com.shkoda.corrector.equations;


/**
 * Created with IntelliJ IDEA.
 * User: Olga GluShko
 * Date: 13.05.13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public class FirstTypeEquationSolver {


    /**
     * creates smth like Gauss. Diagonal matrix  last row is bit answer.
     *
     * @return 2 values, roots of matrix equation
     */


    public static int[] solve(int[][] workMatrix, int sizeOfFirstRoot, int SizeOfSecondRoot) {


        for (int row = 0; row < workMatrix.length; row++) {
            int biggestBeginColumn = row;

            for (int i = row + 1; i < workMatrix.length; i++)
                if (workMatrix[row][i] == 1) {
                    biggestBeginColumn = i;              //the biggest row begin
                    break;
                }

            if (row != biggestBeginColumn) {

                int[] temp = new int[workMatrix.length];
                for (int j = 0; j < workMatrix.length; j++) {      //exchange columns
                    temp[j] = workMatrix[j][biggestBeginColumn];
                    workMatrix[j][biggestBeginColumn] = workMatrix[j][row];
                    workMatrix[j][row] = temp[j];
                }
            }


            for (int j = 0; j < workMatrix.length; j++)           //xor with all other columns
                if ((workMatrix[row][j] == 1) && (j != row)) {
                    for (int k = 0; k < workMatrix.length; k++)
                        workMatrix[k][j] ^= workMatrix[k][row];
                }
        }

        int[] roots = new int[2];

        String firstRootBinary = "";

        roots[0] = 0;
        for (int j = 0; j < sizeOfFirstRoot; j++) {
            roots[0] += workMatrix[workMatrix.length - 1][j] << j;
            firstRootBinary = workMatrix[workMatrix.length - 1][j] + firstRootBinary;
        }

        String secondRootBinary = "";


        roots[1] = 0;
        for (int j = 0; j < SizeOfSecondRoot; j++) {
            roots[1] += workMatrix[workMatrix.length - 1][j + sizeOfFirstRoot] << j;
            secondRootBinary = workMatrix[workMatrix.length - 1][j + sizeOfFirstRoot] + secondRootBinary;
        }

        return roots;
    }
}
