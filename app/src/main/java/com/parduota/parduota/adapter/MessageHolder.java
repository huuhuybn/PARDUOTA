package com.parduota.parduota.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.model.MessageResponse;

/**
 * Created by MAC2015 on 12/4/17.
 */

class MessageHolder extends RecyclerView.ViewHolder {
    final TextView tvFrom;
    final TextView tvMessage;
    final TextView tvDateTime;

    public MessageResponse messageResponse;

    public MessageHolder(View convertView) {
        super(convertView);

        tvFrom = convertView.findViewById(R.id.tvFrom);
        tvMessage = convertView.findViewById(R.id.tvMessage);
        tvDateTime = convertView.findViewById(R.id.tvDateTime);


    }
}
