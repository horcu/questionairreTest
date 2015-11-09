package com.horcu.apps.peez.custom;

import android.widget.ArrayAdapter;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.horcu.apps.common.utilities.consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacz on 11/1/2015.
 */
public class Taglist {

    public static List<String> getActionVerbs() {
        List<String> verbs = new ArrayList<>();
        verbs.add("wins");
        verbs.add("tds");
        verbs.add("yds");
        verbs.add("rush");
        verbs.add("rec");
        verbs.add("ret yds");
        verbs.add("net yds");
        verbs.add("int");
        verbs.add("cat");
        verbs.add("cat a");
        verbs.add("pen yds");
        return verbs;
    }

    public static List<String> getWhen() {
        List<String> verbs = new ArrayList<>();
        verbs.add("week");
        verbs.add("month");
        verbs.add("game");
        verbs.add("1st qtr");
        verbs.add("2nd qtr");
        verbs.add("3rd qtr");
        verbs.add("4th qtr");
        verbs.add("first half");
        verbs.add("second half");
        return verbs;
    }

    public static List<String> getEligible() {
        List<String> verbs = new ArrayList<>();
        verbs.add("quarterback");
        verbs.add("runningback");
        verbs.add("widereceiver");
        verbs.add("tightend");
        verbs.add("defense");
        verbs.add("offense");
        verbs.add("team");
        verbs.add("confenrence");
        verbs.add("division");
        return verbs;
    }

    public static List<String> getVerbs(String entityQuarterback, String structure) throws JSONException {

        Gson gson = new Gson();
        JSONObject Entities = new JSONObject(structure);
        JSONObject stats = Entities.getJSONObject("Stats");

        List<String> allowedVerbs = new ArrayList<>();

        for (int x = 0; x < stats.length(); x++)
        {
            try {
                String key = getActionVerbs().get(x);
                JSONObject thisStat = (JSONObject) stats.get(key);
                if(thisStat.get(consts.ELIGIBLE).toString().contains(entityQuarterback));
                allowedVerbs.add(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  allowedVerbs;
    }
}
