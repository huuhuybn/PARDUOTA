package com.parduota.parduota.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.parduota.parduota.R;

/**
 * Created by huy_quynh on 10/30/17.
 */

class OrderHolder extends RecyclerView.ViewHolder {


    public final TextView tv_title;
    public final TextView tv_status;
    public final TextView tv_ebay_id;
    public final TextView tv_date_time;


    public OrderHolder(View itemView) {
        super(itemView);
        tv_date_time = (TextView) itemView.findViewById(R.id.tv_date_time);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        tv_ebay_id = (TextView) itemView.findViewById(R.id.tv_ebay_id);
    }
}
