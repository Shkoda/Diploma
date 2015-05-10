package com.shkoda.corrector;

import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.results.TripleResult;
import com.shkoda.structures.sums.SigmaCheckSum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.utils.MathUtils.bitOnPosition;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaCorrector {

    private static boolean outputEnabled = true;

    private static QuadraResult differenceInManyBits(boolean[] receivedMessage, SigmaCheckSum correctSum, SigmaCheckSum delta) {
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

    public static QuadraResult solve(boolean[] receivedMessage, SigmaCheckSum correctSum, SigmaCheckSum delta) {
        QuadraResult result1, result2;
        int deltaLambda = -1;
        //1.	Перевіряємо значення контрольної суми Δ0.
        if (delta.oneBitIndexesXor != 0) {
            QuadraResult result = differenceInManyBits(receivedMessage, correctSum, delta);
            if (result != null)
                return result;

        }
        //3.	Якщо Δ0 = 0, то присутні лише пусті (повні) й нульові контрольні суми.
            /*
            3.1.	Шукаємо нульові контрольні суми Δα і Δβ (Δβ ≠ Δα) починаючи з Δ1.
             У будь-якому випадку Δα = A + B = С + В, Δβ = A + C = B + D.
             */

        int deltaA = -1, deltaB = -1;

        for (int i = 0; i <= delta.oneBitOnPositionXor.size(); i++) {
            int deltaK = delta.oneBitOnPositionXor.get(i);
            if (isZeroControlSum(deltaK, i, delta.oneBitIndexesXor)) {

                if (deltaA == -1) {
                    deltaA = deltaK;
                    continue;
                }

                if (deltaA == deltaK){
                    continue;
                }

                deltaB = deltaK;
                break;

//                if (deltaA == -1) {
//                    deltaA = deltaK;
//                } else {
//
//                    deltaB = deltaK;
//                    break;
//                }
            }
        }

        // 3.2.	Обчислюємо Δγ = B + C = A + D = Δα + Δβ.
        int deltaGamma = deltaA ^ deltaB;

            /*
                3.3.	Припускаємо, що є хоча б одна додаткова контрольна сума Δλ, λ = ψ.. ψ+1,
                така, що
                Δλ ≠ Δ0,
                Δλ ≠ Δα,
                Δλ ≠ Δβ,
                Δλ ≠ Δγ.
                 Це буде одинична контрольна сума.
                Шукаємо розв’язок аналогічно до пп. 2.2-2.3.
            */

        int sigma0 = delta.sigma3_0;
        if (sigma0 != delta.oneBitIndexesXor
                && sigma0 != deltaA
                && sigma0 != deltaB
                && sigma0 != deltaGamma) {
            deltaLambda = sigma0;
        } else {
            deltaLambda = delta.sigma3_1;
        }
        print(String.format("delta Lambda = %d\n", deltaLambda));
        result1 = solve(delta, deltaLambda);

        if (result1 != null) {
            print(result1.getLog() + "\n");
            print(result1.toString() + "\n");

        } else {
            print("result1 is null");
        }

        if (isValid(receivedMessage, correctSum, result1))
            return result1;

//        int potentialRoot = deltaLambda ^ delta.oneBitIndexesXor;
        int potentialRoot = deltaLambda ^ deltaGamma;
        print(String.format("potentialRoot= %d\n", potentialRoot));
        result2 = solve(delta, potentialRoot);

        if (result2 != null) {
            print(result2.getLog() + "\n");
            print(result2.toString() + "\n");

        } else {
            print("result2 is null");
        }

        return isValid(receivedMessage, correctSum, result2) ? result2 : null;
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


    private static boolean isValid(boolean[] receivedMessage, SigmaCheckSum correctSum, QuadraResult result) {
        if (result == null)
            return false;
        try {
            boolean[] fixedMessage = result.generateFixedMessage(receivedMessage);
            SigmaCheckSum sum = new SigmaCheckSum(fixedMessage);
            return SigmaCheckSum.equals(correctSum, sum);
        } catch (Exception e) {
            return false;
        }


    }


    private static QuadraResult solve(SigmaCheckSum delta, int potentialRoot) {
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
















