package com.shkoda.corrector;

import com.shkoda.structures.results.TripleResult;
import com.shkoda.utils.Formatter;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * C0reated by Nightingale on 16.04.2015.
 */
public class TripleErrorCorrector {

    public static void main(String[] args) {
/*
Original :: 0, 1, 0, 1, 0, 0, 1, 1,
Damaged  :: 0, 1, 1, 0, 1, 0, 1, 1,
Fixed    :: null

Control sums :: [9, 7, 5, 3, 8]
Bad sums     :: [11, 1, 6, 2, 8]
Delta sums   :: [2, 6, 3, 1, 0]

Real bad positions  :: [3, 4, 5]
Found bad positions :: [3, 1, 0]
 */
        int[] delta = new int[]{2, 6, 3, 1, 0};
//        int[] delta = new int[]{15,9,14,14,15};
        TripleResult result = solve(delta);
        System.out.println(result.log);
        System.out.println(result);
    }

    public static TripleResult solve(int[] delta) {
        StringBuilder sb = new StringBuilder();

        int low = -1, middle = -1, high = -1, tmp = -1;
        int bitNumber = delta.length - 1;
        //1 Номер j поточної різниці контрольних кодів приймача та передавача встановити в n: j= n.
        int j = delta.length - 1;

        sb.append(format("delta = %s\n", Arrays.toString(delta)));
        sb.append(format("1. j := %d\n", j));

        //2 Якщо  delta[j] = delta[0] або delta[j] = 0
        // зменшити на одиницю значення j:  j=j-1 і повернутися на п.2.
        while (delta[j] == delta[0] || delta[j] == 0) {
            j--;
            sb.append(format("2. j = %d\n", j));
        }

        //3 Якщо j-тий розряд delta[j] дорівнює одиниці,
        // то має місце друга ситуація і виконується перехід на п.8 алгоритму.
        if (bitOnPosition(delta[j], j) != 1) {
            sb.append(format("3. bit in %s on position %d == 0\n",
                    Formatter.toBinaryString(delta[j], bitNumber),
                    j));

            //4 Позиція g першого з пошкоджених при передачі блоку бітів
            // визначається як сума: low=delta[tmp] ^ delta[0]. Фіксується значення tmp:  tmp:=j.
            low = delta[j] ^ delta[0];
            tmp = j;

            sb.append(format("4. low = %s[%d] ^ %s[0]\n\tlow = %d\n\ttmp = %d\n",
                    Arrays.toString(delta),
                    j,
                    Arrays.toString(delta),
                    low,
                    tmp));
            //5 Виконується декремент j:  j=j-1.
            // Якщо  delta[j] in {delta[0], delta[tmp], 0}, то перехід на повторне виконання п.5 алгоритму.
            do {
                j--;
                sb.append(format("5. j = %d\n", j));
            }
            while (delta[j] == delta[0] || delta[j] == delta[tmp] || delta[j] == 0);
//

            //6 Якщо delta[j] != delta[0] && delta[j] != delta[tmp] && delta[j] !=  0,
            sb.append(format("6. %s[%d] = %d\n",
                    Arrays.toString(delta),
                    j,
                    delta[j]));

            if (delta[j] != delta[0] && delta[j] != delta[tmp] && delta[j] != 0) {
                sb.append(format("\tbit in %s on position %d = %d\n",
                        Formatter.toBinaryString(low, bitNumber),
                        j,
                        bitOnPosition(low, j)));
                //  то аналізується  j-тий  розряд  визначеного в п.4 коду low:
                // в разі якщо j-тий  розряд  low дорівнює нулю, то перехід на п.7.,

                if (bitOnPosition(low, j) == 1) {
                    // інакше значення позиції high визначається як  high = delta[0] ^ delta[tmp] ^ delta[j],
                    // а значення позиції  middle визначається як сума middle = delta[0] ^ delta[j]. Кінець.
                    high = delta[0] ^ delta[tmp] ^ delta[j];
                    middle = delta[0] ^ delta[j];
                    sb.append(format("\tmiddle = %d, high = %d\n", middle, high));

                    return new TripleResult(low, middle, high, sb.toString());
                } else {
                    //7 Позиція high останнього з пошкоджених при передачі бітів дорівнює delta[j] :
                    // high = delta[j],
                    // а позиція третього пошкодженого біту обчислюється як сума:
                    // middle = delta[tmp] ^ delta[j]. Кінець.
                    high = delta[j];
                    middle = delta[tmp] ^ delta[j];
                    sb.append(format("7. middle = %d, high = %d\n", middle, high));

                    return new TripleResult(low, middle, high, sb.toString());
                }
            }
        }
        //8 Позиція high  останнього зі спотворених при передачі блоку бітів визначається як сума:
        // high  = delta[j].
        // Фіксується значення tmp:  tmp = j.
        high  = delta[j];
        tmp = j;
        sb.append(format("8. high = %d, tmp = %d\n", high, tmp));

        //9 Виконується декремент j:  j=j-1.
        // Якщо delta[j] in {delta[0], delta[tmp], delta[0] ^ delta[tmp], 0},
        // то перехід на повторне виконання п.9 алгоритму.
        do {
            j--;
            sb.append(format("9. j = %d\n", j));
        }
        while (delta[j] == delta[0] ||
                delta[j] == delta[tmp] ||
                delta[j] == (delta[0] ^ delta[tmp]) ||
                delta[j] == 0);

        //10  Якщо delta[j] != delta[0] && delta[j] != delta[tmp] && delta[j] != delta[0] ^ delta[tmp] && delta[j] != 0,
        if (delta[j] != delta[0] &&
                delta[j] != delta[tmp] &&
                delta[j] != (delta[0] ^ delta[tmp]) &&
                delta[j] != 0) {
            sb.append(format("10. delta[%d] = %d\n", j, delta[j]));
            sb.append(format("\tbit in %s on position %d = %d\n", Formatter.toBinaryString(middle, bitNumber), j, bitOnPosition(middle, j)));

            // то аналізується  j-тий  розряд  визначеного в п.8 коду high:
            // якщо j-тий  розряд  high дорівнює нулю, то перехід на п.11.,

            if (bitOnPosition(high, j) == 1) {
                // інакше значення позиції middle  визначається як  middle = delta[j] ^ delta[tmp],
                // а значення позиції  low  обчислюється як сума low =d elta[0] ^ delta[j]. Кінець.
                middle  = delta[j] ^ delta[tmp];
                low = delta[0] ^ delta[j];

                return new TripleResult(low, middle, high, sb.toString());
            }
        }

        //11 Позиція middle середнього  з пошкоджених при передачі бітів дорівнює
        // delta[j] : middle = delta[j],
        // а позиція low першого пошкодженого біту обчислюється як сума:
        // low = delta[0] ^ delta[tmp] ^ delta[j].  Кінець.
        middle = delta[j];
        low = delta[0] ^ delta[tmp] ^ delta[j];

        return new TripleResult(low, middle, high, sb.toString());
    }

    private static int bitOnPosition(int number, int position) {
        return (number >> (position - 1)) & 1;
    }


    public static TripleResult solve(List<Integer> deltaList) {
        int[] delta = new int[deltaList.size()];
        for (int i = 0; i < deltaList.size(); i++) {
            delta[i] = deltaList.get(i);
        }
       return solve(delta);
//        return result.toArray();
    }

}
