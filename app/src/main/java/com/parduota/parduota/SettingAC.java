package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import com.parduota.parduota.abtract.MActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SettingAC extends MActivity {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView tvProfile = findViewById(R.id.tvProfile);
        TextView tvPassword = findViewById(R.id.tvPassword);

        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(ProfileAC.class);
            }
        });
        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(ChangePasswordAC.class);
            }
        });


    }

    public void onProfileClick(View view) {


    }

    public void onPasswordClick(View view) {


    }

}
