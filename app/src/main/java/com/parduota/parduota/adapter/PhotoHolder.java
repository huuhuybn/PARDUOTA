package com.parduota.parduota.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.parduota.parduota.R;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class PhotoHolder extends RecyclerView.ViewHolder {


    final ImageView imgAvatar;
    final ImageView btnDelete;
    final ProgressBar progressBar;


    public PhotoHolder(View convertView) {
        super(convertView);
        imgAvatar =  convertView.findViewById(R.id.img_avatar);
        btnDelete =  convertView.findViewById(R.id.btn_delete);
        progressBar = convertView.findViewById(R.id.progress_bar);


    }
}
