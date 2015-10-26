package com.horcu.apps.peez.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.horcu.apps.peez.R;

public class FailureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
            ((TextView)findViewById(R.id.failure_text)).setText(extras.getString("failure_text","Something went wrong.."));

    }

}
