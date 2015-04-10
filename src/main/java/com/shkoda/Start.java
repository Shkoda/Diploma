package com.shkoda;

import com.shkoda.corrector.DoubleErrorCorrector;
import com.shkoda.generator.MessageGenerator;
import com.shkoda.sum.CheckSum;
import com.shkoda.utils.Formatter;

import java.util.List;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Start {
    public static void main(String[] args) {
        boolean[] message = MessageGenerator.generateMessage(new int[]{0, 1, 1, 0, 1, 1, 0});
        System.out.println(Formatter.toString(message));
        List<Integer> correctSums = CheckSum.count(message);
        System.out.println("Correct sums :: "+correctSums);


        boolean[] damagedMessage = MessageGenerator.invertBits(message, 1, 4);
        System.out.println(Formatter.toString(damagedMessage));
        List<Integer> badSums = CheckSum.count(damagedMessage);
        System.out.println("Bad sums :: "+badSums);


        boolean[] fixed = DoubleErrorCorrector.fix(damagedMessage, correctSums);

        System.out.println("-----------------------------------");
        System.out.println(Formatter.toString(fixed));

        //time to start diploma =)
    }
}
