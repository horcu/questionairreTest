package com.horcu.apps.peez.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.gcm.message.Message;

public class ChallengeView extends AppCompatActivity {

    public String moveTo = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null)
        {
            moveTo = bundle.getString(consts.EXTRAS_MOVE_TO);
        }
    }


}
