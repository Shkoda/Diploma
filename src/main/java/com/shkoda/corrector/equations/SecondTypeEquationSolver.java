package com.shkoda.corrector.equations;

import java.util.*;

public class SecondTypeEquationSolver {

    public static void main(String[] args) {
        System.out.println("solution :: "+solve(3, 5, 4)[0]);
    }

    public static int[] solve(int xoredXandY, int multipliedXandY, int answerLength) {

        char[] symbolsOfUnknownBits = createSymbolsOfUnknownBits(answerLength);

        Map<Character, Integer> answerBits = createAnswerBitsStructure(answerLength);

        int[] xorColumn = createXorColumn(xoredXandY, answerLength);

        ArrayList<ArrayList<Character>> matrix = createEquationMatrix(multipliedXandY, xorColumn, answerLength, symbolsOfUnknownBits);
        firstMinimization(matrix, answerBits);

        if ((findUndefinedBitIndex(answerBits) != '#') && (matrix.size() > 0)) {

            defineRandomBitPair(matrix, answerBits);

            secondMinimization(matrix, answerBits);

        }

        char index = findUndefinedBitIndex(answerBits);
        answerBits.put(index, 1);

        int root1 = defineFirstEquationRoot(answerLength, answerBits);
        int root2 = defineSecondEquationRoot(root1, xoredXandY);

        return new int[]{root1, root2};
    }

    /**
     * Find first equation with two unknown variables.
     * Define firstVariable = 1, secondVariable = 1 ^ equationRightSideValue
     *
     * @param matrix
     * @param answerBits
     */

    private static void defineRandomBitPair(ArrayList<ArrayList<Character>> matrix, Map<Character, Integer> answerBits) {
        List<Character> columnWithTwoVariables = matrix.get(0);

        for (ArrayList<Character> aMatrix : matrix) {
            columnWithTwoVariables = aMatrix;
            if (columnWithTwoVariables.size() == 3)
                break;
        }

        answerBits.put(columnWithTwoVariables.get(0), 1);             //set 2 new bits
        answerBits.put(columnWithTwoVariables.get(1), ((columnWithTwoVariables.get(2) - 48) ^ 1));

    }


    private static char[] createSymbolsOfUnknownBits(int answerLength) {
        char[] answer = new char[answerLength];
        char t = 'a';

        for (int i = 0; i < answerLength; i++) {
            answer[i] = (char) (t + i);
        }
        return answer;
    }

    private static Map<Character, Integer> createAnswerBitsStructure(int answerLength) {

        Map<Character, Integer> answerBits = new HashMap<>();
        char t = 'a';                              //symbols of X-bits

        for (int i = 0; i < answerLength; i++) {
            answerBits.put((char) (t + i), -1);
        }
        return answerBits;
    }


    /**
     * G-vector
     *
     * @param controlSumXor
     * @return
     */

    private static int[] createXorColumn(int controlSumXor, int size) {

        int[] xorColumn = new int[size];
        int temp = controlSumXor;

        for (int i = 0; i < size; i++) {
            xorColumn[i] = temp % 2;
            temp /= 2;
        }

        return xorColumn;

    }

    /**
     * Matrix is created as list of columns.
     *
     * @param xorColumn
     * @param answerLength
     * @param symbolsOfUnknownBits
     * @return
     */

    private static ArrayList<ArrayList<Character>> createEquationMatrix(int multipliedXandY, int[] xorColumn, int answerLength, char[] symbolsOfUnknownBits) {

        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        int xRowLength = 2 * answerLength - 1;

        //---------------empty matrix filled by '-' and '0'

        for (int column = 0; column < xRowLength; column++) {
            matrix.add(new ArrayList<Character>());
            for (int row = 0; row < xorColumn.length + 2; row++)
                if (row == xorColumn.length + 1)
                    matrix.get(column).add('0');
                else matrix.get(column).add('-');
        }

        //---------arguments in matrix with offset. multiple by G-vector (xorColumn) inclusive

        for (int row = 0; row < xorColumn.length; row++) {
            if (xorColumn[row] != 0) {
                int offset = xRowLength - symbolsOfUnknownBits.length - row;
                for (int column = 0; column < symbolsOfUnknownBits.length; column++)
                    matrix.get(offset + column).set(row, symbolsOfUnknownBits[column]);
            }
        }

        //fill last row. Its multiplier is always 1.

        for (int i = 0; i < answerLength; i++)
            matrix.get(2 * i).set(answerLength, symbolsOfUnknownBits[i]);

        //---------bottom row - result of column values xor
        int temp = multipliedXandY;
        for (int i = 0; i < xRowLength; i++) {
            matrix.get(xRowLength - i - 1).set(matrix.get(i).size() - 1, (char) (temp % 2 + 48));
            temp /= 2;
        }


        return matrix;


    }

    /**
     * A xor A = 0, so equal variables are useless in column. Mark them as trash and remove.
     *
     * @param column
     */

    private static void removeEqualVariablesFromColumn(ArrayList<Character> column) {
        List<Character> trash = new ArrayList<>();
        trash.add('-');

        for (int i = 0; i < column.size() - 2; i++) {
            char currentSymbol = column.get(i);
            for (int j = i + 1; j < column.size() - 1; j++)
                if (column.get(j).equals(currentSymbol)) {         //remove equal in column
                    column.set(i, '-');
                    column.set(j, '-');
                }
        }
        column.removeAll(trash);                            //remove '-'
    }


    private static void firstMinimization(ArrayList<ArrayList<Character>> matrix, Map<Character, Integer> answerBits) {

        List<Character> trash = new ArrayList<>();
        trash.add('-');

        for (Iterator<ArrayList<Character>> matrixIterator = matrix.iterator(); matrixIterator.hasNext(); ) {
            ArrayList<Character> currentColumn = matrixIterator.next();

            currentColumn.removeAll(trash);

            removeEqualVariablesFromColumn(currentColumn);

            removeAllDefinedBits(currentColumn, answerBits);    //if some bits are already defined, remove them from column


            if (currentColumn.size() < 2) {                              //if column hasn't useful information, remove it
                matrixIterator.remove();
                continue;

            }

            if (currentColumn.size() == 2) {                         //new bit can be defined
                answerBits.put(currentColumn.get(0), (currentColumn.get(1) - 48));  //in iscii 0 has code 48
                matrixIterator.remove();
                matrixIterator = matrix.iterator();                       //return to matrix begin
            }

            if (findUndefinedBitIndex(answerBits) == '#')
                return;
        }
    }

    private static void secondMinimization(ArrayList<ArrayList<Character>> matrix, Map<Character, Integer> answerBits) {

        for (Iterator<ArrayList<Character>> matrixIterator = matrix.iterator(); matrixIterator.hasNext(); ) {
            ArrayList<Character> currentColumn = matrixIterator.next();
            removeAllDefinedBits(currentColumn, answerBits);
            if (currentColumn.size() < 2)                                  //if column is empty, remove it
                matrixIterator.remove();

            else if (currentColumn.size() == 2) {                         //new bit can be defined
                answerBits.put(currentColumn.get(0), (currentColumn.get(1) - 48));  //in iscii 0 has code 48
                matrixIterator.remove();
                matrixIterator = matrix.iterator();                       //return to matrix begin
            }
            if (findUndefinedBitIndex(answerBits) == '#')
                break;
        }
    }


    /**
     * @param map
     * @return index or '#' if all bits were defined
     */
    public static char findUndefinedBitIndex(Map<Character, Integer> map) {

        for (Map.Entry<Character, Integer> characterIntegerEntry : map.entrySet())
            if (characterIntegerEntry.getValue().equals(-1))
                return characterIntegerEntry.getKey();
        return '#';
    }

    /**
     * Inspects column. If column contains defined bits, change it with '-'.
     * Removes all trash from column ('-' symbols).
     *
     * @param column
     * @param answerBits
     */

    private static void removeAllDefinedBits(ArrayList<Character> column, Map<Character, Integer> answerBits) {

        List<Character> trash = new ArrayList<>();
        trash.add('-');

        for (int i = 0; i < column.size() - 1; i++)
            if (answerBits.get(column.get(i)) != -1) {
                column.set(column.size() - 1, (char) ((column.get(column.size() - 1) - 48) ^ answerBits.get(column.get(i)) + 48));
                column.set(i, '-');
            }

        column.removeAll(trash);
    }

    private static int defineFirstEquationRoot(int answerLength, Map<Character, Integer> answerBits) {
        int root1 = 0;

        for (int i = 0; i < answerLength; i++)
            root1 += (1 << i) * answerBits.get((char) ('a' + answerLength - 1 - i));

        return root1;
    }

    private static int defineSecondEquationRoot(int firstRoot, int deltaXor) {
        return firstRoot ^ deltaXor;
    }

}



