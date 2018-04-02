package com.parduota.parduota;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.fragment.PhotoFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.item.Datum;
import com.parduota.parduota.model.item.Item;
import com.parduota.parduota.model.item.Medium;
import com.parduota.parduota.model.notification.MetaData;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ItemDetailAC extends MActivity implements Constant {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_item_detail;
    }

    private Datum datum;

    private MetaData metaData;

    private String type_from;


    private ViewPager pagers;
    private TextView tvTitle;
    private TextView tvDateTime;
    private TextView tvPrice;
    private TextView tvQuality;
    private TextView tvDetail;
    private TextView tvAddress;
    private TextView tvCondition;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        pagers = (ViewPager) findViewById(R.id.pagers);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDateTime = (TextView) findViewById(R.id.tv_date_time);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvQuality = (TextView) findViewById(R.id.tv_quality);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvCondition = (TextView) findViewById(R.id.tv_condition);

        String from = getIntent().getStringExtra(FROM);
        type_from = from;
        if (from.equals(ITEM_SCREEN)) {
            datum = new Gson().fromJson(getIntent().getStringExtra(DATA), Datum.class);
            if (actionBar != null) actionBar.setTitle(datum.getTitle());

            tvTitle.setText(datum.getTitle());
            tvDateTime.setText(datum.getCreatedAt());

            PhotoItemAdapter photoAdapter = new PhotoItemAdapter(getSupportFragmentManager(), datum.getMedia());
            pagers.setAdapter(photoAdapter);


        }
        if (from.equals(NOTIFICATION_SCREEN)) {
            metaData = new Gson().fromJson(getIntent().getStringExtra(DATA), MetaData.class);
            if (actionBar != null) actionBar.setTitle(metaData.getTitle());
            tvTitle.setText(metaData.getTitle());
            tvDateTime.setText(metaData.getCreatedAt());


            Log.e("URL", new Gson().toJson(metaData));
            PhotoNotiAdapter photoNotiAdapter = new PhotoNotiAdapter(getSupportFragmentManager(), metaData.getMedia());
            pagers.setAdapter(photoNotiAdapter);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, EditItemAC.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);

    }

    public class PhotoNotiAdapter extends FragmentStatePagerAdapter {

        public List<com.parduota.parduota.model.notification.Medium> mediums;

        public PhotoNotiAdapter(FragmentManager fm, List<com.parduota.parduota.model.notification.Medium> mediums) {
            super(fm);
            this.mediums = mediums;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.createInstance(mediums.get(position).getLink());
        }

        @Override
        public int getCount() {
            return mediums != null ? mediums.size() : 0;
        }

    }

    public class PhotoItemAdapter extends FragmentStatePagerAdapter {

        public List<Medium> mediums;

        public PhotoItemAdapter(FragmentManager fm, List<Medium> mediums) {
            super(fm);
            this.mediums = mediums;
        }

        @Override
        public Fragment getItem(int position) {
            return PhotoFragment.createInstance(mediums.get(position).getLink());
        }

        @Override
        public int getCount() {
            return mediums != null ? mediums.size() : 0;
        }

    }


}
