package com.parduota.parduota;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.Button;
import android.widget.TextView;

import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ExtendOrderDetailActivity extends MActivity implements Constant {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_extend_order_detail;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        String title = intent.getStringExtra(TITLE);
        final String link = intent.getStringExtra(LINK);
        String des = intent.getStringExtra(DESCRIPTION);

        setTitle(title);

        TextView tvTitle = findViewById(R.id.tvTitle);
        Button btnViewInEbay = findViewById(R.id.btnViewInEbay);
        TextView tvDescription = findViewById(R.id.tvDescription);
        tvTitle.setText(title);
        btnViewInEbay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(link));
                    startActivity(i);

                } catch (Exception ignored) {

                }
            }
        });

        tvDescription.setText(des);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
