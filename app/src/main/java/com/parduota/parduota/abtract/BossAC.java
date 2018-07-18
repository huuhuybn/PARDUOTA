package com.parduota.parduota.abtract;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.parduota.parduota.utils.SharePrefManager;

import java.util.Locale;

public class BossAC extends AppCompatActivity {
    protected SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharePrefManager = SharePrefManager.getInstance(this);

        // set locale before run activity


        Locale locale = new Locale(sharePrefManager.getLocale());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


    }

}
