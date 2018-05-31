package com.parduota.parduota;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parduota.parduota.ion.Constant;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhotoViewAC extends AppCompatActivity implements Constant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_view);
        ImageView image = findViewById(R.id.image);
        String url = getIntent().getStringExtra(DATA);

        Glide.with(this).load(url).into(image);

    }


}
