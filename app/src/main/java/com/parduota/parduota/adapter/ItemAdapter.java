package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.item.Datum;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 10/31/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> implements Constant {

    public Context context;
    public ArrayList<Datum> items;

    public ItemAdapter(Context context, ArrayList<Datum> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Datum datum = items.get(position);
        holder.datum = datum;
        holder.tv_title.setText(datum.getTitle());
        holder.tv_price.setText(context.getString(R.string.hint_price) + ": " + datum.getPrice());
        holder.tv_quality.setText(datum.getQuantity() + "");
        holder.tv_time.setText(datum.getCreatedAt());
        holder.tv_status.setText(datum.getStatus()+"");
        //holder.tv_status.setBackgroundColor(datum.getColor_status());
        if (datum.getMedia().size() > 0)
            Glide.with(context).load(PHOTO_URL + datum.getMedia().get(0).getLink()).into(holder.img_avatar);

        holder.itemView.setOnClickListener(holder.onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
