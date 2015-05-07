package com.shkoda.corrector;

import com.shkoda.generator.MessageGenerator;
import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.results.TripleResult;
import com.shkoda.structures.sums.CheckSum;
import com.shkoda.structures.sums.SigmaCheckSum;
import com.shkoda.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.shkoda.utils.MathUtils.*;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaCorrector {
    public static QuadraResult solve(boolean[] receivedMessage, SigmaCheckSum correctSum, SigmaCheckSum delta) {
        QuadraResult result1, result2;
        int deltaLambda = -1;
        //1.	Перевіряємо значення контрольної суми Δ0.
        if (delta.oneBitIndexesXor != 0) {
            //  2.	Якщо Δ0 ≠ 0, то присутня хоча б одна одинична контрольна сума Δλ, λ = 1..k.
            // 2.1.	Шукаємо Δλ.


            for (int i = 0; i <= delta.oneBitOnPositionXor.size(); i++) {
                int deltaK = delta.oneBitOnPositionXor.get(i);
                if (isOneControlSum(deltaK, i, delta.oneBitIndexesXor)) {
                    deltaLambda = deltaK;
                    break;
                }
            }

//            deltaLambda = delta.oneBitOnPositionXor.stream()
//                    .filter(deltaK -> deltaK != 0)
//                    .filter(deltaK -> deltaK != delta.oneBitIndexesXor)
//                    .findFirst()
//                    .get();
            System.out.println(String.format("delta Lambda = %d\n", deltaLambda));
            //     2.2.	Знаходимо пару розв’язків (порядок A, B, C, D не важливий):
            /*  a)	Припускаємо, що Δλ = A. Маємо один розв’язок A = Δλ.
                Змініюємо j-ті контрольні суми Δj = Δj + A, якщо aj = 1.
                З допомогою зміненої контрольної суми шукаємо 3-кратну помилку.
                */
            result1 = solve(delta, deltaLambda);

            System.out.println(result1.getLog() + "\n");
            System.out.println(result1);

            System.out.println();

            if (isValid(receivedMessage, correctSum, result1))
                return result1;

            /* b)	Припускаємо, що Δλ = B + C + D.
                    Маємо один розв’язок A = Δ0 + Δλ.
                    Аналогічно до п. 2.2a змінюємо
                    контрольні суми й шукаємо 3-кратну помилку.
             */
            int potentialRoot = deltaLambda ^ delta.oneBitIndexesXor;
            System.out.println(String.format("potentialRoot = %d\n", potentialRoot));
            result2 = solve(delta, potentialRoot);
            System.out.println(result2.getLog() + "\n");
            System.out.println(result2);

        } else {
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
                    } else {
                        deltaB = deltaK;
                        break;
                    }
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

            int sigma0 = delta.sigma0;
            if (sigma0 != delta.oneBitIndexesXor
                    && sigma0 != deltaA
                    && sigma0 != deltaB
                    && sigma0 != deltaGamma) {
                deltaLambda = sigma0;
            } else {
                deltaLambda = delta.sigma1;
            }
            System.out.println(String.format("delta Lambda = %d\n", deltaLambda));
            result1 = solve(delta, deltaLambda);

            System.out.println(result1.getLog() + "\n");
            System.out.println(result1);

            System.out.println();

            if (isValid(receivedMessage, correctSum, result1))
                return result1;
            int potentialRoot = deltaLambda ^ delta.oneBitIndexesXor;
            System.out.println(String.format("potentialRoot= %d\n", potentialRoot));
            result2 = solve(delta, potentialRoot);
            System.out.println(result2.getLog() + "\n");
            System.out.println(result2);

        }

        return isValid(receivedMessage, correctSum, result2) ? result2 : null;
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
        boolean[] fixedMessage = result.generateFixedMessage(receivedMessage);
        SigmaCheckSum sum = new SigmaCheckSum(fixedMessage);
        return SigmaCheckSum.equals(correctSum, sum);

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
















