package com.shkoda.corrector;

import com.shkoda.structures.results.QuadraResult;
import com.shkoda.structures.results.TripleResult;
import com.shkoda.structures.sums.SigmaCheckSum;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nightingale on 07.05.2015.
 */
public class SigmaCorrector {
    public static QuadraResult solve(SigmaCheckSum delta) {
        QuadraResult result1, result2;
        int deltaLambda;
        //1.	Перевіряємо значення контрольної суми Δ0.
        if (delta.oneBitIndexesXor != 0) {
            //  2.	Якщо Δ0 ≠ 0, то присутня хоча б одна одинична контрольна сума Δλ, λ = 1..k.
            // 2.1.	Шукаємо Δλ.
            deltaLambda = delta.oneBitOnPositionXor.stream()
                    .filter(deltaK -> deltaK != 0)
                    .findFirst()
                    .get();

            //     2.2.	Знаходимо пару розв’язків (порядок A, B, C, D не важливий):
            /*  a)	Припускаємо, що Δλ = A. Маємо один розв’язок A = Δλ.
                Змініюємо j-ті контрольні суми Δj = Δj + A, якщо aj = 1.
                З допомогою зміненої контрольної суми шукаємо 3-кратну помилку.
                */
            result1 = solve(delta, deltaLambda);

            /* b)	Припускаємо, що Δλ = B + C + D.
                    Маємо один розв’язок A = Δ0 + Δλ.
                    Аналогічно до п. 2.2a змінюємо
                    контрольні суми й шукаємо 3-кратну помилку.
             */
            result2 = solve(delta, deltaLambda ^ delta.oneBitIndexesXor);
        } else {
            //3.	Якщо Δ0 = 0, то присутні лише пусті (повні) й нульові контрольні суми.
            /*
            3.1.	Шукаємо нульові контрольні суми Δα і Δβ (Δβ ≠ Δα) починаючи з Δ1.
             У будь-якому випадку Δα = A + B = С + В, Δβ = A + C = B + D.
             */
            List<Integer> deltas = delta.oneBitOnPositionXor.stream()
                    .filter(deltaK -> deltaK != 0)
                    .collect(Collectors.toList());
            // 3.2.	Обчислюємо Δγ = B + C = A + D = Δα + Δβ.
            int deltaGamma = deltas.get(0) ^ deltas.get(1);

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
                    && sigma0 != deltas.get(0)
                    && sigma0 != deltas.get(1)
                    && sigma0 != deltaGamma) {
                deltaLambda = sigma0;
            } else {
                deltaLambda = delta.sigma1;
            }

            result1 = solve(delta, deltaLambda);
            result2 = solve(delta, deltaLambda ^ delta.oneBitIndexesXor);
        }

        System.out.println(String.format("delta Lambda = %d\n", deltaLambda));

        System.out.println(result1.getLog()+"\n");
        System.out.println(result1);
//        System.out.println(result2);

        return null;
    }

    private static QuadraResult solve(SigmaCheckSum delta, int potentialRoot) {
        List<Integer> modifiedDelta = xorWithValue(delta.oneBitOnPositionXor, potentialRoot);
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
















