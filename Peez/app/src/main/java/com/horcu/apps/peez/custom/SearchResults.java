package com.horcu.apps.peez.custom;

import com.google.gson.annotations.SerializedName;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;

import java.util.List;

/**
 * Created by Mustafa Ali on 11/03/15.
 */
public class SearchResults {
    public int totalItems;
    @SerializedName("items")
    public List<Bet> bets;
}
