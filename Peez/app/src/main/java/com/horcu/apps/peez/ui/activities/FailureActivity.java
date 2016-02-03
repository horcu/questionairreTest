package com.horcu.apps.peez.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.horcu.apps.peez.R;

public class FailureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            ((TextView) findViewById(R.id.failure_text)).setText(extras.getString("failure_text", "Something went wrong.."));

    }

}
