package com.horcu.apps.peez.ui.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.horcu.apps.peez.R;

import com.horcu.apps.peez.ui.fragments.testItemFragment;


public class MainActivity extends AppCompatActivity implements testItemFragment.OnFragmentInteractionListener {
    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
