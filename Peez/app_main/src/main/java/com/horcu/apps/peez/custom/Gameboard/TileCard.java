package com.horcu.apps.peez.custom.Gameboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.horcu.apps.peez.chat.LeBubbleTitleTextView;
import com.horcu.apps.peez.custom.UserImageView;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.CircleButton;

/**
 * Created by hcummings on 3/18/2016.
 */
public class TileCard extends CardView {

    //TODO find a way to set the line color dynamically

    private UserImageView scoreBadge;
    private CircleButton playerPiece;


    public TileCard(Context context, UserImageView scoreBadge, CircleButton playerPiece, LeBubbleTitleTextView tileInfoBox) {
        super(context);
        this.scoreBadge = scoreBadge;
        this.playerPiece = playerPiece;
        scoreBadge = new UserImageView(context, null);
        playerPiece = new CircleButton(context, null);
    }

    public TileCard(Context context, AttributeSet attrs, UserImageView scoreBadge, CircleButton playerPiece, LeBubbleTitleTextView tileInfoBox) {
        super(context, attrs);
        this.scoreBadge = scoreBadge;
        this.playerPiece = playerPiece;
        scoreBadge = new UserImageView(context, null);
        playerPiece = new CircleButton(context, null);
    }

    public TileCard(Context context, AttributeSet attrs, int defStyleAttr, UserImageView scoreBadge, CircleButton playerPiece, LeBubbleTitleTextView tileInfoBox) {
        super(context, attrs, defStyleAttr);
        this.scoreBadge = scoreBadge;
        this.playerPiece = playerPiece;
        scoreBadge = new UserImageView(context, null);
        playerPiece = new CircleButton(context, null);
    }

    public UserImageView getScoreBadge() {
        return scoreBadge;
    }

    public void setScoreBadge(UserImageView scoreBadge) {
        this.scoreBadge = scoreBadge;
    }

    public CircleButton getPlayerPiece() {
        return playerPiece;
    }

    public void setPlayerPiece(CircleButton playerPiece) {
        this.playerPiece = playerPiece;
    }

}
