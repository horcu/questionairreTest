package com.horcu.apps.peez.custom.Gameboard;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.View;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.custom.MaterialLetterIcon;

/**
 * Created by Horatio on 3/27/2016.
 */
public final class TileMover {


    private static CardView lastDestinationCard;
    private static CardView currentDestinationCard;
    private static View lastViewMoved;

    public static Boolean SwapTiles(CardView oldCard, CardView newCard){

            try {
                MaterialLetterIcon movingIcon = (MaterialLetterIcon)oldCard.getChildAt(0);
                oldCard.removeView(movingIcon);
                MaterialLetterIcon containerIcon = (MaterialLetterIcon) newCard.getChildAt(0);
                newCard.removeView(containerIcon);
                newCard.addView(movingIcon);
                oldCard.addView(containerIcon);
                YoYo.with(Techniques.Pulse).duration(1000).playOn(containerIcon);
                YoYo.with(Techniques.Pulse).duration(1000).playOn(movingIcon);

                lastDestinationCard = oldCard;
                currentDestinationCard = newCard;
                lastViewMoved = movingIcon;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
    }

    //MAIN MOVEMENT FUNCTIONS
    public static Boolean ReverseLastPlay(){
        try {
            if(lastDestinationCard != null && currentDestinationCard != null && lastViewMoved != null)
            SwapTiles(currentDestinationCard, lastDestinationCard);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean EatTile(CardView oldCard, CardView newCard){
        try{
            ReverseLastPlay();
            oldCard.getChildAt(0).setBackgroundResource(R.drawable.ic_used);
            ((MaterialLetterIcon)oldCard.getChildAt(0)).setShapeColor(Color.TRANSPARENT);

            return true;
        }
        catch(Exception ex){

        }

        return true;}

    public static Boolean SetSnare(CardView oldCard, CardView newCard){
        try{
            ReverseLastPlay();
            oldCard.getChildAt(0).setBackgroundResource(R.drawable.ic_block);
            ((MaterialLetterIcon)oldCard.getChildAt(0)).setShapeColor(Color.TRANSPARENT);

            return true;
        }
        catch(Exception ex){

        }
        return true;
    }

    public CardView getOldCard() {
        return lastDestinationCard;
    }

    public CardView getNewCard() {
        return currentDestinationCard;
    }
    //END MAIN MOVEMENT FUNCTIONS


    //AUDITS
    private boolean PassEatAudit() {
        return true;
    }

    private boolean PassSnareAudit() {
        return true;
    }

    private boolean PassMoveAudit() {
        return true;
    }
    //END AUDITS
}
