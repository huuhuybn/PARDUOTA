package com.parduota.parduota.add;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parduota.parduota.AddAC;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.adapter.UploadPhotoAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.Photo;
import com.parduota.parduota.model.UploadItem;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class FraAddPhoto extends MFragment {


    private TextView tvNotifyAddImage;

    private UploadPhotoAdapter uploadPhotoAdapter;

    // class variables
    private static final int REQUEST_CODE = 123;

    public ArrayList<AlbumFile> getmAlbumFiles() {
        return mAlbumFiles;
    }

    public void setmAlbumFiles(ArrayList<AlbumFile> mAlbumFiles) {
        this.mAlbumFiles = mAlbumFiles;
    }

    private ArrayList<AlbumFile> mAlbumFiles;

    private int numberUpload = 0;


    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_photo;
    }

    @Override
    protected void initView(View view) {

        mAlbumFiles = new ArrayList<>();


        tvNotifyAddImage = view.findViewById(R.id.tv_notify_add_image);


        RecyclerView lvList = view.findViewById(R.id.lv_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);


        uploadPhotoAdapter = new UploadPhotoAdapter(getActivity(), mAlbumFiles);
        lvList.setLayoutManager(gridLayoutManager);
        lvList.setAdapter(uploadPhotoAdapter);

        view.findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.image(getActivity()) // Image selection.
                        .multipleChoice()
                        .camera(true)
                        .columnCount(3)
                        .selectCount(12)
                        .checkedList(mAlbumFiles)// Show the filtered files, but they are not available.
                        .onResult(new Action<ArrayList<AlbumFile>>() {
                            @Override
                            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                                tvNotifyAddImage.setVisibility(View.GONE);
                                mAlbumFiles.addAll(result);


                                uploadPhotoAdapter.notifyDataSetChanged();
                                numberUpload = result.size();
                            }
                        })
                        .onCancel(new Action<String>() {
                            @Override
                            public void onAction(@NonNull String result) {
                            }
                        })
                        .start();
            }
        });


        AddAC addAC = (AddAC) getActivity();
        if ((addAC).getData() != null) {
            ArrayList<AlbumFile> files;
            UploadItem uploadItem = addAC.getUploadItem();

            files = uploadItem.getPhotoArr();

            if (files.size() > 0) {
                mAlbumFiles.addAll(files);
                uploadPhotoAdapter.notifyDataSetChanged();

                tvNotifyAddImage.setVisibility(View.GONE);

                if (Constant.isDEBUG)
                    Log.e("AAAAAAAAA", new Gson().toJson(mAlbumFiles));
            }

        }

    }


    @Override
    protected void setData(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


}
