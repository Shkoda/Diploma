package com.shkoda;

import com.shkoda.generator.MessageGenerator;
import com.shkoda.utils.Formatter;

/**
 * Created by Nightingale on 10.04.2015.
 */
public class Start {
    public static void main(String[] args) {
        boolean[] message = MessageGenerator.generateMessage(8);
        boolean[] damagedMessage = MessageGenerator.damagedMessage(message, 0, 2, 4);

        System.out.println(Formatter.toString(message));
        System.out.println(Formatter.toString(damagedMessage));

        //time to start diploma =)
    }
}
