package com.parduota.parduota.adapter;

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
 * Created by huy_quynh on 10/31/17.
 */

class ItemHolder extends RecyclerView.ViewHolder implements Constant {


    private Datum datum;

    public ItemHolder(final View itemView) {
        super(itemView);

        TextView tv_status = itemView.findViewById(R.id.tv_status);
        ImageView img_avatar = itemView.findViewById(R.id.img_avatar);
        TextView tv_time = itemView.findViewById(R.id.tv_time);
        TextView tv_title = itemView.findViewById(R.id.tv_title);
        TextView tv_quality = itemView.findViewById(R.id.tv_quality);
        TextView tv_price = itemView.findViewById(R.id.tv_price);
        View.OnClickListener onClickListener = new View.OnClickListener() {
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
