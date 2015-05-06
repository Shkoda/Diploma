package com.shkoda.corrector;

import java.util.List;

/**
 * Created by Nightingale on 06.05.2015.
 */
public class CorrectErrorsInTwoBits {
    public int[] solve(List<Integer> deltas) {
        int[] errors = new int[5];

        int thirds = deltas.get(1);
       int modulo = thirds % 3;
        switch (modulo){
            case 1:
                //индексы средние на 3 делятся, крайние - нет

        }




        return errors;
    }


}
