package com.shkoda.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Olga GluShko
 * Date: 13.05.13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ModuleIntervalMath {
    /**
     * received - correct
     *
     * @param correctEoS
     * @param newEoS
     * @return
     */

    public static int deltaEoS(int correctEoS, int newEoS) {

        if (correctEoS == newEoS)
            return 0;


        switch (correctEoS) {
            case 0:
                if (newEoS == 7) {
                    return -1;
                } else {
                    if (newEoS == 6) {
                        return -2;
                    } else
                        return (newEoS - correctEoS) % 4;
                }

            case 1:
                if (newEoS == 7)
                    return -2;
                else
                    return (newEoS - correctEoS) % 4;


            case 6:
                if (newEoS == 0)
                    return 2;
                else return (newEoS - correctEoS) % 4;

            case 7:
                if (newEoS == 0)
                    return 1;
                else if (newEoS == 1)
                    return 2;
                else return (newEoS - correctEoS) % 4;


            default:
                return (newEoS - correctEoS) % 4;
        }

    }

}
