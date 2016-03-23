package com.horcu.apps.peez.view.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChallengeViewFragment extends Fragment {

    public ChallengeViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_challenge_view, container, false);

        root.findViewById(R.id.goback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wonChallenge()) {

                    String moveTo = ((ChallengeView)getActivity()).moveTo;
                    Intent intent = new Intent();
                    intent.putExtra(consts.EXTRAS_MOVE_TO, moveTo);

                    getActivity().setResult(consts.INTENT_TO_CHALLENGE, intent);
                    getActivity().finish();
                }
            }
        });


        return root;

    }

    private boolean wonChallenge() {
        return true;
    }
}
