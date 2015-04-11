package com.shkoda;

import com.shkoda.corrector.DoubleErrorCorrector;
import com.shkoda.corrector.TripleErrorCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.sum.CheckSum;
import com.shkoda.utils.Formatter;
import com.shkoda.utils.MathUtils;

import java.util.Arrays;
import java.util.List;

import static com.shkoda.utils.Formatter.logError;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Start {
    public static void main(String[] args) {
        //{0,1,1,0,1,1,0}
        boolean[] message = MessageGenerator.generateMessage(new int[]{ 1, 0, 0, 1, 1, 0, 0});
        List<Integer> sum = CheckSum.count(message);

        int[] errors = new int[]{3, 2, 4};

        boolean[] badMessage = MessageGenerator.invertBits(message, errors);

        List<Integer> badSum = CheckSum.count(badMessage);

        boolean[] fixed = null;
        try {

            fixed = TripleErrorCorrector.fix(badMessage, sum);
        }catch (Exception e){

        }finally {
            logError(message, badMessage, fixed, sum, badSum, errors);
        }




        //time to start diploma =)
    }
}
