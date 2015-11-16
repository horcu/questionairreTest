package com.horcu.apps.peez.custom;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


/**
 * Created by hacz on 11/11/2015.
 */
public class ViewController {


    public ViewController showThis(View v, Techniques technique) {

        if(v != null && v.getVisibility() == View.GONE) {
            YoYo.with(technique)
                    .duration(200)
                    .playOn(v);
            v.setVisibility(View.VISIBLE);
        }
        return ViewController.this;
    }

    public ViewController hideThis(View v, Techniques technique) {

        if(v != null && v.getVisibility() == View.VISIBLE) {
            YoYo.with(technique)
                    .duration(200)
                    .playOn(v);
            v.setVisibility(View.GONE);
        }
        return ViewController.this;
    }

}
