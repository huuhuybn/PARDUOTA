package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.upload.DesFra;
import com.parduota.parduota.upload.OtherFra;
import com.parduota.parduota.upload.TitleFra;
import com.parduota.parduota.view.UploadDialog;

/**
 * Created by huy_quynh on 7/20/17.
 */

public class UploadAC extends MActivity implements Constant {


    private EditText edt_title;
    private Button btn_next;
    private ViewPager pagers;

    private String title;
    private String description;
    private int status;
    private int category;
    private int price;
    private String country;
    private String address;


    @Override
    protected int setLayoutId() {
        return R.layout.act_up_load;
    }

    private BroadcastReceiver broadcastReceiver;

    private UpItemAdapter upItemAdapter;

    @Override
    protected void initView() {

        pagers = (ViewPager) findViewById(R.id.pagers);
        upItemAdapter = new UpItemAdapter(getSupportFragmentManager());
        pagers.setAdapter(upItemAdapter);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("ANC", "Next");
                pagers.setCurrentItem(pagers.getCurrentItem() + 1);
                int index = intent.getIntExtra(INDEX, -1);
                String content = intent.getStringExtra(DATA);
                if (index > 0) {
                    switch (index) {
                        case INPUT_TITLE:
                            title = content;
                        case INPUT_DES:
                            description = content;
                        case INPUT_OTHER:

                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_NEXT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    class UpItemAdapter extends FragmentPagerAdapter {

        public UpItemAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TitleFra();
                case 1:
                    return new DesFra();
                case 2:
                    return new OtherFra();
                case 3:
                    return new DesFra();
                case 4:
                    return new DesFra();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
