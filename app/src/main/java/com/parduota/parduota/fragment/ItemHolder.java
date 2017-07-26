package com.parduota.parduota.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.model.item.Datum;
import com.parduota.parduota.view.DetailDialog;

/**
 * Created by huy_quynh on 6/28/17.
 */

class ItemHolder extends RecyclerView.ViewHolder {


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

        tv_status = (TextView) itemView.findViewById(R.id.tv_status);
        img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_quality = (TextView) itemView.findViewById(R.id.tv_quality);
        tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailDialog detailDialog = new DetailDialog(itemView.getContext(), datum.getDescription());
                detailDialog.show();
            }
        };

    }
}
