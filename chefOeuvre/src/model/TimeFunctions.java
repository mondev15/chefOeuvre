/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines static functions to convert time representations
 * 
 * @author Charlelie
 */
public class TimeFunctions {

    public static int hmsToInt(String hms) {
        List<String> splitted = new ArrayList(Arrays.asList(hms.split(":")));
        return (Integer.parseInt(splitted.get(0)) * 3600
                + Integer.parseInt(splitted.get(1)) * 60
                + Integer.parseInt(splitted.get(2)));
    }

    public static String intTohms(int time) {
        String format = "%02d";
        int h = time / 3600;
        time -= h * 3600;
        int m = time / 60;
        time -= m * 60;
        return (String.format(format, h) + ":" + String.format(format, m) + ":" + String.format(format, time));
    }
}
