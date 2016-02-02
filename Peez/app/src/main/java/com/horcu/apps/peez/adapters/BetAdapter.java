package com.horcu.apps.peez.adapters;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.horcu.apps.peez.R;
import com.horcu.apps.peez.backend.models.betApi.model.Bet;
import com.horcu.apps.peez.databinding.BetRowBinding;

import java.util.List;
/**
 * Created by hacz on 10/14/2015.
 */
/**
 * Created by phanirajabhandari on 7/6/2015.
 */
public class BetAdapter extends RecyclerView.Adapter<BetAdapter.BindingHolder> {
    private List<Bet> bets;

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private BetRowBinding binding;

        public BindingHolder(View v) {
            super(v);

        }

        public BetRowBinding getBinding() {
            return binding;
        }
    }

    public BetAdapter(List<Bet> movies) {
        this.bets = movies;
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
        final Bet bet = bets.get(position);
       // holder.getBinding().setVariable(BR.bet, bet);
       // holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {

        return bets != null ? bets.size() : 0;
    }
}
