package com.parduota.parduota.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.parduota.parduota.R;

/**
 * Created by MAC2015 on 12/9/17.
 */

class ChargerHolder extends RecyclerView.ViewHolder {


    final TextView tvTitle;
    final TextView tvCredit;
    final TextView tvTime;


    public ChargerHolder(View convertView) {
        super(convertView);
        tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvCredit = (TextView) convertView.findViewById(R.id.tvCredit);
        tvTime = (TextView) convertView.findViewById(R.id.tvTime);

    }
}
