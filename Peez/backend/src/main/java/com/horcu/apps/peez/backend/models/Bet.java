package com.horcu.apps.peez.backend.models;

import com.google.api.client.util.DateTime;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.horcu.apps.peez.backend.models.league.Team;


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
    private String[] acceptersRegIds; //json string with list of accepters of this bet
    private String[] declinersRegIds; //json string with list of people who rejected this bet


    private String[] Bets; // text string json that represent the betting points eg. more than 300 yds, less than 21 total points
    private double Wager;
    private NFLPlayer player;
    private Team team;
    private java.util.Date betMadeAt;
    private java.util.Date betAcceptedAt;
    private java.util.Date betDeclinedAt;

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

    public String[] getAcceptersRegIds() {
        return acceptersRegIds;
    }

    public void setAcceptersRegIds(String[] acceptersRegIds) {
        this.acceptersRegIds = acceptersRegIds;
    }

    public String[] getDeclinersRegIds() {
        return declinersRegIds;
    }

    public void setDeclinersRegIds(String[] declinersRegIds) {
        this.declinersRegIds = declinersRegIds;
    }

    public NFLPlayer getPlayer() {
        return player;
    }

    public void setPlayer(NFLPlayer player) {
        this.player = player;
    }

    public java.util.Date getBetMadeAt() {
        return betMadeAt;
    }

    public void setBetMadeAt(java.util.Date betMadeAt) {
        this.betMadeAt = betMadeAt;
    }

    public java.util.Date getBetAcceptedAt() {
        return betAcceptedAt;
    }

    public void setBetAcceptedAt(java.util.Date betAcceptedAt) {
        this.betAcceptedAt = betAcceptedAt;
    }

    public java.util.Date getBetDeclinedAt() {
        return betDeclinedAt;
    }

    public void setBetDeclinedAt(java.util.Date betDeclinedAt) {
        this.betDeclinedAt = betDeclinedAt;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
