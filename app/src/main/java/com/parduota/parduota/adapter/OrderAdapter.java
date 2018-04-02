package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parduota.parduota.R;
import com.parduota.parduota.face.OnOrderClick;
import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.model.order.Order;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 10/30/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {


    public Context context;
    public ArrayList<Datum> orders;
    public OnOrderClick orderOnItemClick;

    public OrderAdapter(Context context, ArrayList<Datum> orders) {
        this.context = context;
        this.orders = orders;
    }

    public OrderAdapter(Context context, ArrayList<Datum> orders, OnOrderClick orderOnItemClick) {
        this.context = context;
        this.orders = orders;
        this.orderOnItemClick = orderOnItemClick;
    }


    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {

        final Datum order = orders.get(position);
        holder.tv_title.setText(order.getTitle());
        holder.tv_ebay_id.setText(order.getEbayId());
        holder.tv_status.setText(order.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderOnItemClick.onClick(order);
            }
        });
        holder.tv_date_time.setText(order.getCreatedAt());


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
