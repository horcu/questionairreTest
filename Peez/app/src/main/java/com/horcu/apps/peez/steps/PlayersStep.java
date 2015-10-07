package com.horcu.apps.peez.steps;

import android.content.Context;
import android.view.View;

/**
 * Created by hacz on 10/7/2015.
 */
public class PlayersStep extends Step {
    public PlayersStep(Context context, String dataKey, int titleResId, int errorResId, int detailsResId) {
        super(context, dataKey, titleResId, errorResId, detailsResId);
    }

    public PlayersStep(Context context, String dataKey, String title, String error, String details) {
        super(context, dataKey, title, error, details);
    }

    @Override
    public View onCreateView() {
        return null;
    }

    @Override
    public void updateView(boolean lastStep) {

    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    protected void onSave() {

    }

    @Override
    protected void onRestore() {

    }
}
