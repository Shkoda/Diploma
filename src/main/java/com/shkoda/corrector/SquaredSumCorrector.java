package com.shkoda.corrector;

import com.shkoda.corrector.equations.EquationSystemSolver;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.Pair;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.results.TripleResult;
import com.shkoda.structures.sums.SquaredCheckSum;
import com.shkoda.sum.CheckSumCounter;
import com.shkoda.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.sum.CheckSumCounter.count;
import static com.shkoda.utils.MathUtils.bitOnPosition;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SquaredSumCorrector {

    private static boolean outputEnabled = false;

    public static QuadraResult solve(boolean[] receivedMessage, SquaredCheckSum correctSum, SquaredCheckSum receivedSum, SquaredCheckSum delta) {
        QuadraResult result1, result2;
        int deltaLambda = -1;
        //1.	Перевіряємо значення контрольної суми Δ0.
        if (delta.oneBitIndexesXor != 0) {
            QuadraResult result = nonZeroSumSolution(receivedMessage, correctSum, delta);
            if (result != null)
                return result;
        }

        int cmSen = -1, cmRec = -1, deltaEoS = -1, xor = -1;

        for (int i = 0; i < delta.oneBitOnPositionXor.size(); i++) {
            if (delta.oneBitOnPositionXor.get(i) != 0) {
                xor = delta.oneBitOnPositionXor.get(i);
                cmSen = correctSum.multOneBitOnPositionXor.get(i);
                cmRec = receivedSum.multOneBitOnPositionXor.get(i);
                deltaEoS = delta.eos.get(i);

                break;
            }
        }

        Pair roots = EquationSystemSolver.solve(delta.bitNumber, xor, cmSen, cmRec, deltaEoS);

        boolean[] modifiedMessage = MessageGenerator.invertBits(receivedMessage, roots.x, roots.y);

        SquaredCheckSum modifiedSum = new SquaredCheckSum(modifiedMessage);

        Pair otherErrors = DoubleErrorCorrector.errorPositions(correctSum.delta(modifiedSum));

        QuadraResult result = new QuadraResult(roots.x, roots.y, otherErrors.x, otherErrors.y);

        return result;

    }


    private static QuadraResult nonZeroSumSolution(boolean[] receivedMessage, SquaredCheckSum correctSum, SquaredCheckSum delta) {
        QuadraResult result1, result2;
        int deltaLambda = -1;

        //  2.	Якщо Δ0 ≠ 0, то присутня хоча б одна одинична контрольна сума Δλ, λ = 1..k.
        // 2.1.	Шукаємо Δλ.

        for (int i = 0; i < delta.oneBitOnPositionXor.size(); i++) {
            int deltaK = delta.oneBitOnPositionXor.get(i);
            if (isOneControlSum(deltaK, i, delta.oneBitIndexesXor)) {
                deltaLambda = deltaK;
                break;
            }
        }

        if (deltaLambda == -1){
            int A = -1;
            //no one-control-sum
            for (int i = 0; i < delta.oneBitOnPositionXor.size(); i++) {
                int deltaK = delta.oneBitOnPositionXor.get(i);
                if (deltaK==delta.oneBitIndexesXor) {
                    A = deltaK;
                    break;
                }
            }

            boolean[] modifiedMessage = MessageGenerator.invertBits(receivedMessage, A);
            List<Integer> badSum = count(modifiedMessage);
            List<Integer> correct = count(correctSum);

            TripleResult tripleResult = TripleErrorCorrector.solve(MathUtils.xor(correct, badSum));
            return new QuadraResult(A, tripleResult.high, tripleResult.middle, tripleResult.low);
        }

        print(String.format("delta Lambda = %d\n", deltaLambda));
        if (deltaLambda == -1)
            return null;
        //     2.2.	Знаходимо пару розв’язків (порядок A, B, C, D не важливий):
            /*  a)	Припускаємо, що Δλ = A. Маємо один розв’язок A = Δλ.
                Змініюємо j-ті контрольні суми Δj = Δj + A, якщо aj = 1.
                З допомогою зміненої контрольної суми шукаємо 3-кратну помилку.
                */
        result1 = solve(delta, deltaLambda);

        if (result1 != null) {
            print(result1.getLog() + "\n");
            print(result1.toString() + "\n");

        } else {
            print("result1 is null");
        }

        if (isValid(receivedMessage, correctSum, result1))
            return result1;

            /* b)	Припускаємо, що Δλ = B + C + D.
                    Маємо один розв’язок A = Δ0 + Δλ.
                    Аналогічно до п. 2.2a змінюємо
                    контрольні суми й шукаємо 3-кратну помилку.
             */
        int potentialRoot = deltaLambda ^ delta.oneBitIndexesXor;
        print(String.format("potentialRoot = %d\n", potentialRoot));
        result2 = solve(delta, potentialRoot);
        print(result2.getLog() + "\n");
        print(result2);
        return result2;
    }


    private static void print(Object s) {
        if (outputEnabled)
            System.out.println(s);
    }

    private static boolean isEmptyControlSum(int deltaK, int delta0) {
        return deltaK == 0;
    }

    private static boolean isFullControlSum(int deltaK, int delta0) {
        return deltaK == delta0;
    }

    private static boolean isZeroControlSum(int deltaK, int k, int delta0) {
        return !isEmptyControlSum(deltaK, delta0)
                && !isFullControlSum(deltaK, delta0)
                && bitOnPosition(deltaK, k + 1) == 0;
    }

    private static boolean isOneControlSum(int deltaK, int k, int delta0) {
        return !isEmptyControlSum(deltaK, delta0)
                && !isFullControlSum(deltaK, delta0)
                && bitOnPosition(deltaK, k + 1) == 1;
    }


    private static boolean isValid(boolean[] receivedMessage, SquaredCheckSum correctSum, QuadraResult result) {
        if (result == null)
            return false;
        try {
            boolean[] fixedMessage = result.generateFixedMessage(receivedMessage);
            SquaredCheckSum sum = new SquaredCheckSum(fixedMessage);
            return SquaredCheckSum.equals(correctSum, sum);
        } catch (Exception e) {
            return false;
        }


    }


    private static QuadraResult solve(SquaredCheckSum delta, int potentialRoot) {
        List<Integer> modifiedDelta = new ArrayList<>(delta.oneBitOnPositionXor);

        for (int i = 0; i < modifiedDelta.size(); i++) {
            int current = modifiedDelta.get(i);
            if (bitOnPosition(potentialRoot, i + 1) == 1)
                modifiedDelta.set(i, current ^ potentialRoot);
        }

//        List<Integer> modifiedDelta = xorWithValue(delta.oneBitOnPositionXor, potentialRoot);
        modifiedDelta.add(0, delta.oneBitIndexesXor ^ potentialRoot);

        TripleResult tripleResult = TripleErrorCorrector.solve(modifiedDelta);
        if (tripleResult == null)
            return null;
        int[] tripleSolution = tripleResult.toArray();
        return new QuadraResult(potentialRoot, tripleSolution[0], tripleSolution[1], tripleSolution[2])
                .setLog(tripleResult.log);
    }

    private static List<Integer> xorWithValue(List<Integer> list, int value) {
        return list.stream()
                .map(i -> i ^ value)
                .collect(Collectors.toList());
    }
}
















