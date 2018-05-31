package com.parduota.parduota.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.UploadResponse;
import com.parduota.parduota.utils.SharePrefManager;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class UploadPhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {


    private final Context context;
    private final ArrayList<AlbumFile> photos;
    private final String token;


    public UploadPhotoAdapter(Context context, ArrayList<AlbumFile> photos) {
        this.context = context;
        this.photos = photos;
        SharePrefManager sharePrefManager = SharePrefManager.getInstance(context);
        this.token = sharePrefManager.getAccessToken();
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoHolder holder,int position) {

        final AlbumFile photo = photos.get(holder.getAdapterPosition());
        Glide.with(context).load(photo.getThumbPath()).into(holder.imgAvatar);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photos.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        if (photo.getWidth() == 0)
            ION.uploadFile(context, holder.progressBar, token, Constant.URL_UPLOAD, new File(photo.getPath()), new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    try {
                        UploadResponse uploadResponse = new Gson().fromJson(result, UploadResponse.class);
                        Intent intent = new Intent(UploadResponse.class.getName());
                        intent.putExtra(UploadResponse.class.getName(), uploadResponse.getMedium().getId());
                        context.sendBroadcast(intent);

                        photos.get(holder.getAdapterPosition()).setWidth(uploadResponse.getMedium().getId());
                        notifyDataSetChanged();

                    } catch (Exception ignored) {

                    }
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        else holder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}
