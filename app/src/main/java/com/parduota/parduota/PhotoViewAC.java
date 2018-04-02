package com.parduota.parduota;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.library.ImageLoader;
import com.parduota.parduota.ion.Constant;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoViewAC extends AppCompatActivity implements Constant {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_view);
        image = (ImageView) findViewById(R.id.image);
        String url = getIntent().getStringExtra(DATA);
        ImageLoader.loadImage(this, url, image);


    }


}
