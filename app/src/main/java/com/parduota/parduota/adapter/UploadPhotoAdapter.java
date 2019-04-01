package com.parduota.parduota.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.LoginActivity;
import com.parduota.parduota.R;
import com.parduota.parduota.com.github.zulhilmizainuddin.httprequest.Http;
import com.parduota.parduota.com.github.zulhilmizainuddin.httprequest.HttpDelete;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.UploadResponse;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.utils.SharePrefManager;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        if (Constant.isDEBUG) Log.e("Size photo", photos.size() + "");
        SharePrefManager sharePrefManager = SharePrefManager.getInstance(context);
        this.token = sharePrefManager.getAccessToken();
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoHolder holder, int position) {

        final AlbumFile photo = photos.get(position);
        Glide.with(context).load(photo.getThumbPath()).into(holder.imgAvatar);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photos.remove(holder.getAdapterPosition());
                notifyDataSetChanged();

                // i used mediatype as photo id;

                delPhoto(photo.getMediaType());


            }
        });

        if (photo.getMediaType() == 1)
            ION.uploadFile(context, holder.progressBar, token, Constant.URL_UPLOAD, new File(photo.getPath()), new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    //if (Constant.isDEBUG) Log.e("Upload", result.toString());

                    try {
                        UploadResponse uploadResponse = new Gson().fromJson(result, UploadResponse.class);
                        Intent intent = new Intent(UploadResponse.class.getName());
                        intent.putExtra(UploadResponse.class.getName(), uploadResponse.getMedium().getId());
                        context.sendBroadcast(intent);

                        photos.get(holder.getAdapterPosition()).setMediaType(uploadResponse.getMedium().getId());
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



    // not sure why retrofit not working with delete method, that why's i used this code instead of retrofit delete request
    public void delPhoto(final int id) {

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    Http http = new HttpDelete(Constant.URL_DELETE_PHOTO + id);

                    int responseCode =
                            http.setHeader("Accept-Encoding", "gzip, deflate")
                                    .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36")
                                    .setConnectionTimeout(15000).setHeader("Authorization", "Bearer " + token)
                                    .setReadTimeout(15000)
                                    .setFollowRedirects(true)
                                    .execute();

                    Map<String, List<String>> responseHeaders;
                    List<HttpCookie> responseCookies;
                    String responseBody;
                    String redirectUrl;

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        responseHeaders = http.getResponseHeaders();
                        responseCookies = http.getResponseCookies();
                        responseBody = http.getResponseBody();

                        Log.e("response", responseBody);
                        redirectUrl = http.getRedirectUrl();
                    }

                    // Process response
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };

        asyncTask.execute();

    }

}
