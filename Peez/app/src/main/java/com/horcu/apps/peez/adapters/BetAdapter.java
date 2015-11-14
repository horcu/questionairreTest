package com.horcu.apps.peez.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by hacz on 10/14/2015.
 */
public class BetAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] objects;

    public BetAdapter(Context context, int resource) {
        super(context, resource);
    }

    public BetAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public BetAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    public BetAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    public BetAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public BetAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
