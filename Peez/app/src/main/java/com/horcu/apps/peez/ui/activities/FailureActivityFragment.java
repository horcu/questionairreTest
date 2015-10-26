package com.horcu.apps.peez.ui.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.horcu.apps.peez.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FailureActivityFragment extends Fragment {

    public FailureActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_failure, container, false);
    }
}
