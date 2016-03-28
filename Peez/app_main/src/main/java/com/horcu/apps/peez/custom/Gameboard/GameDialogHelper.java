package com.horcu.apps.peez.custom.Gameboard;

import android.content.Context;
import android.view.View;

import rebus.bottomdialog.BottomDialog;

/**
 * Created by Horatio on 3/27/2016.
 */

public class GameDialogHelper {

    private Context context;

    public GameDialogHelper(Context context){
        this.context = context;
    }

    public BottomDialog BuildDialog(String title, Boolean cancelOnTouchOutside, Boolean cancelable, int menu, BottomDialog.OnItemSelectedListener listener){

        BottomDialog  dialog = new BottomDialog(context);
        dialog.title(title);
        dialog.canceledOnTouchOutside(cancelOnTouchOutside);
        dialog.cancelable(cancelable);
        dialog.inflateMenu(menu);
        dialog.setOnItemSelectedListener(listener);

        return dialog;
    }


}
