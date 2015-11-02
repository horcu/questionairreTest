package com.horcu.apps.peez.custom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacz on 11/1/2015.
 */
public class Taglist {

    public static List<String> getVerbs() {
        List<String> verbs = new ArrayList<>();
        verbs.add("will gain");
        verbs.add("will have");
        verbs.add("will");
        verbs.add("more...");
        return verbs;
    }

    public static List<String> getActionVerbs() {
        List<String> verbs = new ArrayList<>();
        verbs.add("win");
        verbs.add("touchdown");
        verbs.add("total yds");
        verbs.add("rush yds");
        verbs.add("rec yards");
        verbs.add("return yds");
        verbs.add("net yds");
        verbs.add("ints");
        verbs.add("sacks");
        verbs.add("fumbles");
        verbs.add("turnovers");
        verbs.add("penalties");
        verbs.add("penalty yds");
        verbs.add("catches");
        verbs.add("pass attempts");
        verbs.add("completions");
        verbs.add("drops");
        verbs.add("injuries");
        verbs.add("tackles");
        return verbs;
    }

    public static List<String> getWhen() {
        List<String> verbs = new ArrayList<>();
        verbs.add("game");
        verbs.add("1st qtr");
        verbs.add("2nd qtr");
        verbs.add("3rd qtr");
        verbs.add("4th qtr");
        verbs.add("first half");
        verbs.add("second half");
        return verbs;
    }
}
