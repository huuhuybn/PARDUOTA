package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parduota.parduota.R;
import com.parduota.parduota.model.charger.Datum;

import java.util.List;

/**
 * Created by MAC2015 on 12/9/17.
 */

public class ChargerAdapter extends RecyclerView.Adapter<ChargerHolder> {


    private final Context context;
    private final List<Datum> charger;

    public ChargerAdapter(Context context, List<Datum> charger) {
        this.context = context;
        this.charger = charger;
    }

    @Override
    public ChargerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_charger, parent, false);
        return new ChargerHolder(view);
    }

    @Override
    public void onBindViewHolder(ChargerHolder holder, int position) {

        holder.tvCredit.setText(charger.get(position).getCredit() + " " + context.getString(R.string.tv_credit) + " - " + charger.get(position).getType());
        holder.tvTime.setText(charger.get(position).getCreatedAt());

    }

    @Override
    public int getItemCount() {
        if (charger != null) {
            try {
                return charger.size();
            } catch (NullPointerException e) {
                return 0;
            }
        } else return 0;
    }
}
