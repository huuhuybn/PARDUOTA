package com.parduota.parduota.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.parduota.parduota.R;

/**
 * Created by huy_quynh on 6/18/17.
 */

class NotifyHolder extends RecyclerView.ViewHolder {

    public final TextView tv_title, tv_time;

    public NotifyHolder(View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_time = itemView.findViewById(R.id.tv_time);
    }
}
