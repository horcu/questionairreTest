package com.horcu.apps.peez.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hacz on 10/7/2015.
 */
public class BetTag implements Parcelable {

    private String BetId;
    private String GameId;
    private String Bet;
    private double Wager;


    public BetTag() {
    }

    public BetTag(String betId, String gameId, String bet, double wager) {
        BetId = betId;
        GameId = gameId;
        Bet = bet;
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

    public String getBet() {
        return Bet;
    }

    public void setBet(String bet) {
        Bet = bet;
    }

    public double getWager() {
        return Wager;
    }

    public void setWager(double wager) {
        Wager = wager;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.BetId);
        dest.writeString(this.GameId);
        dest.writeString(this.Bet);
        dest.writeDouble(this.Wager);
    }

    protected BetTag(Parcel in) {
        this.BetId = in.readString();
        this.GameId = in.readString();
        this.Bet = in.readString();
        this.Wager = in.readDouble();
    }

    public static final Parcelable.Creator<BetTag> CREATOR = new Parcelable.Creator<BetTag>() {
        public BetTag createFromParcel(Parcel source) {
            return new BetTag(source);
        }

        public BetTag[] newArray(int size) {
            return new BetTag[size];
        }
    };
}
