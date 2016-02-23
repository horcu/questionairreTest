package com.horcu.apps.peez.custom;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.horcu.apps.peez.model.app.obs.FriendViewModel;

/**
 * Created by evan on 6/3/15.
 */
public class Listeners {
    private FriendViewModel viewModel;
    
    public Listeners(FriendViewModel viewModel) {
        this.viewModel = viewModel;
    }
    
    public final View.OnClickListener addItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onAddItem(v);
        }
    };
    
    public final View.OnClickListener removeItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onRemoveItem(v);
        }
    };
    
    public void onAddItem(View v) {
       // viewModel.addItem();
        Snackbar.make(v,"added", Snackbar.LENGTH_LONG).show();
    }
    
    public void onRemoveItem(View v) {
       // viewModel.removeItem();
        Snackbar.make(v,"removed", Snackbar.LENGTH_LONG).show();
    }
}
