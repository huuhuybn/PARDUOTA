package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.utils.SharePrefManager;

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


        // if user used Facebook to login, he doesnt need this button
        if (sharePrefManager.isFacebookLogin())
            tvPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startNewActivity(ChangePasswordAC.class);
                }
            });
        else tvPassword.setVisibility(View.GONE);


        RadioGroup radioGroup = findViewById(R.id.radioLanguage);

        if (sharePrefManager.getLocale().equals(SharePrefManager.ENG)) {
            radioGroup.check(radioGroup.getChildAt(0).getId());
        } else {
            radioGroup.check(radioGroup.getChildAt(1).getId());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.english:
                        sharePrefManager.setLocale(SharePrefManager.ENG);
                        recreate();
                        break;
                    case R.id.lithuania:
                        sharePrefManager.setLocale(SharePrefManager.LT);
                        recreate();
                        break;
                }

            }
        });
    }

    public void onProfileClick(View view) {


    }

    public void onPasswordClick(View view) {


    }

}
