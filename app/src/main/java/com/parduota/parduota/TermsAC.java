package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;

import com.parduota.parduota.abtract.MActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TermsAC extends MActivity {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_history_charge;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        String token = sharePrefManager.getAccessToken();
        RecyclerView lvList = findViewById(R.id.lv_list);

    }

}
