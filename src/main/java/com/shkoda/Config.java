package com.shkoda;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Olga GluShko
 * Date: 10.05.13
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class Config {

    public static final Map<Integer, Integer> POLYNOMIAL_MAP;

    static {
        POLYNOMIAL_MAP = new HashMap<>();
        POLYNOMIAL_MAP.put(4, 299);
        POLYNOMIAL_MAP.put(5, 1283);
        POLYNOMIAL_MAP.put(6, 8379);
        POLYNOMIAL_MAP.put(7, 57687);
    }


}
