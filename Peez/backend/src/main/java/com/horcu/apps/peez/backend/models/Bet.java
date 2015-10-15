package com.horcu.apps.peez.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by hacz on 10/7/2015.
 */
@Entity
public class Bet {

    @Id
    @Index
    private String BetId;

    @Index
    private String GameId;

    private String betterRegId;
    private String[] acceptersRegIds;
    private String[] declinersRegIds;


    private String[] Bets;
    private double Wager;
    private NFLPlayer player;
    private Date betMadeAt;
    private Date betAcceptedAt;
    private Date betdeclinedAt;

    public Bet() {
    }

    public Bet(String betterRegId, String betId, String gameId, String[] bets, double wager) {
        this.betterRegId = betterRegId;
        BetId = betId;
        GameId = gameId;
        Bets = bets;
        Wager = wager;
    }

    public String getBetId() {
        return BetId;
    }

    public void setBetId(String betId) {
        BetId = betId;
    }

    public String getGameId() {
        return GameId;
    }

    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public String[] getBet() {
        return Bets;
    }

    public void setBet(String[] bet) {
        Bets = bet;
    }

    public double getWager() {
        return Wager;
    }

    public void setWager(double wager) {
        Wager = wager;
    }

    public String getBetterRegId() {
        return betterRegId;
    }
}
