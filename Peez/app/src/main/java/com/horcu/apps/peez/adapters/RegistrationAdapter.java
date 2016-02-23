package com.horcu.apps.peez.adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.horcu.apps.peez.BR;
import com.horcu.apps.peez.R;

import com.horcu.apps.peez.backend.registration.model.RegistrationRecord;


import java.util.List;
/**
 * Created by hacz on 10/14/2015.
 */
/**
 * Created by phanirajabhandari on 7/6/2015.
 */
public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.BindingHolder> {
    private List<RegistrationRecord> registrationRecords;

    public static class BindingHolder extends RecyclerView.ViewHolder {
        //private BetRowBinding binding;

        public BindingHolder(View v) {
            super(v);

        }

//        public BetRowBinding getBinding() {
//            return binding;
//        }
    }

    public RegistrationAdapter(List<RegistrationRecord> regs) {
        this.registrationRecords = regs;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.bet_row, parent, false);

        BindingHolder holder = new BindingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final RegistrationRecord reg = registrationRecords.get(position);
       // holder.getBinding().setVariable(BR.bet, reg);
      //  holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {

        return registrationRecords != null ? registrationRecords.size() : 0;
    }
}
