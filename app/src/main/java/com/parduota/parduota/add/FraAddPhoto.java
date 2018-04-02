package com.parduota.parduota.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.adapter.UploadPhotoAdapter;
import com.parduota.parduota.model.Photo;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class FraAddPhoto extends MFragment {


    private static final int REQUEST_IMAGE_CAPTURE = 999;
    private static final int REQUEST_IMAGE_PICK = 888;
    private GridLayoutManager gridLayoutManager;
    private RecyclerView lvList;
    private TextView tvNotifyAddImage;
    private FloatingActionButton btnCapture;

    private ArrayList<Photo> photos;
    private UploadPhotoAdapter uploadPhotoAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_photo;
    }

    @Override
    protected void initView(View view) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }


        tvNotifyAddImage = (TextView) view.findViewById(R.id.tv_notify_add_image);
        btnCapture = (FloatingActionButton) view.findViewById(R.id.btn_capture);

        photos = new ArrayList<>();

        lvList = (RecyclerView) view.findViewById(R.id.lv_list);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);


        uploadPhotoAdapter = new UploadPhotoAdapter(getActivity(), photos);
        lvList.setLayoutManager(gridLayoutManager);
        lvList.setAdapter(uploadPhotoAdapter);
//        btnCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        });
//
//        btnPickPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), REQUEST_IMAGE_PICK);
//            }
//        });

    }

    @Override
    protected void setData(View view) {



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Photo photo = new Photo();
            photo.setBitmap(imageBitmap);
            photos.add(photo);
            uploadPhotoAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_IMAGE_PICK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

        }
    }
}
