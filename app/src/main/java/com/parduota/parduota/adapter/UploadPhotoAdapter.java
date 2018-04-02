package com.parduota.parduota.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.library.ImageLoader;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.Photo;

import java.util.ArrayList;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class UploadPhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {


    private Context context;
    private ArrayList<Photo> photos;


    public UploadPhotoAdapter(Context context, ArrayList<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, final int position) {

        Photo photo = photos.get(position);
        holder.imgAvatar.setImageBitmap(photo.getBitmap());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photos.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
