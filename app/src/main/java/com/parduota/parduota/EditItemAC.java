package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.item.Datum;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class EditItemAC extends MActivity implements Constant {

    private Datum datum;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_item;
    }

    @Override
    protected void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String data = getIntent().getStringExtra(DATA);
        datum = new Gson().fromJson(data, Datum.class);



    }

}
