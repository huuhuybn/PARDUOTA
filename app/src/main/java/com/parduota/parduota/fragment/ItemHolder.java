package com.parduota.parduota.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parduota.parduota.ItemDetailAC;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.item.Datum;

/**
 * Created by huy_quynh on 6/28/17.
 */

class ItemHolder extends RecyclerView.ViewHolder implements Constant {


    public final ImageView img_avatar;
    public final TextView tv_price;
    public final TextView tv_time;
    public final TextView tv_title;
    public final TextView tv_quality;
    public final TextView tv_status;

    public final View.OnClickListener onClickListener;

    public Datum datum;

    public ItemHolder(final View itemView) {
        super(itemView);

        tv_status = itemView.findViewById(R.id.tv_status);
        img_avatar = itemView.findViewById(R.id.img_avatar);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_quality = itemView.findViewById(R.id.tv_quality);
        tv_price = itemView.findViewById(R.id.tv_price);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(itemView.getContext(), ItemDetailAC.class);
                intent.putExtra(FROM, ITEM_SCREEN);
                intent.putExtra(DATA, new Gson().toJson(datum));
                itemView.getContext().startActivity(intent);

            }
        };

    }
}
