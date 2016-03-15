package com.horcu.apps.peez.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.horcu.apps.peez.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends Fragment {


    public ColorPickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_colorpicker_view, container, false);

        ArrayList<CircleButton> buttons = GetCircleButtons();
        GridView grid = (GridView) root.findViewById(R.id.colorpicker_grid);
        ArrayAdapter<CircleButton> adapter = new ColorPickerAdapter<CircleButton>(getContext(), R.layout.fragment_colorpicker_view,R.layout.color_choice,buttons);

        for(int i=0; i < 15; i++)
        {

            //grid.getAdapter()
        }
        return root;
    }

    private ArrayList<CircleButton> GetCircleButtons() {
        return new ArrayList<>();
    }

    private class ColorPickerAdapter<T> extends ArrayAdapter<CircleButton> {
        public ColorPickerAdapter(Context context, int fragment_colorpicker_view, int color_choice, ArrayList<T> buttons) {
            super(context, fragment_colorpicker_view);
        }

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public CircleButton getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

          return null;
        }
    }
}
