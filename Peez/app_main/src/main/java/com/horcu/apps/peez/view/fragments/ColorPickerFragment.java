package com.horcu.apps.peez.view.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.horcu.apps.peez.R;
import com.horcu.apps.peez.common.utilities.consts;
import com.horcu.apps.peez.custom.AutoFitGridLayout;
import at.markushi.ui.CircleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends Fragment {


    private SharedPreferences settings;
    private int[] colors;
    private OnColorChosenListener mListener;

    public ColorPickerFragment() {
        // Required empty public constructor
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnColorChosenListener {
        void OnColorChosen(int color);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnColorChosenListener) {
            mListener = (OnColorChosenListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_colorpicker_view, container, false);
        settings = getActivity().getSharedPreferences("Peez", 0);
        AutoFitGridLayout grid = (AutoFitGridLayout) root.findViewById(R.id.colorpicker_grid);
        Resources r = getResources();
        colors = r.getIntArray(R.array.Colors);
        int clrCount = (grid.getChildCount());
        grid.setBackground(new ColorDrawable(Color.WHITE));
        for(int i=0; i < clrCount ; i++)
        {
            final int color = colors[i];
            CircleButton cb = (CircleButton) grid.getChildAt(i);
            cb.setTag(color);
            SetButtonColor(cb,i);
            final int finalI = i;
            final int finalI1 = i;
            cb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    settings.edit().putInt(consts.FAV_COLOR, finalI1).apply();
                    mListener.OnColorChosen(finalI);
                }
            });
        }
        return root;
    }

    private CircleButton SetButtonColor(CircleButton cb, int  index) {
        cb.setColor(GetColorFromList(index));
        return cb;
    }

    private int GetColorFromList(int index) {
        return colors[index];
    }


}
