package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.notification.MetaData;
import com.parduota.parduota.model.order.Datum;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OrderDetailAC extends MActivity implements Constant {


    private Datum datum;

    private MetaData metaData;
    private String type;

    private Datum orderDetail;

    private Snackbar snackbar;

    private String detail;

    private BroadcastReceiver onCommingMessage;
    private IntentFilter intentFilter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        type = getIntent().getStringExtra(TYPE);
        orderDetail = new Datum();

        if (type.equals(TYPE_ORDER_LIST)) {
            datum = new Gson().fromJson(getIntent().getStringExtra(DATA), Datum.class);
            setTitle(datum.getTitle());
            orderDetail.setId(datum.getId());
            orderDetail.setTitle(datum.getTitle());
            orderDetail.setCreatedAt(datum.getCreatedAt());
            orderDetail.setCreatedBy(datum.getCreatedBy());
            orderDetail.setStatus(datum.getStatus());
            orderDetail.setEbayId(datum.getEbayId());
            orderDetail.setNotice(datum.getNotice());
        } else if (type.equals(NOTIFICATION_SCREEN)) {
            metaData = new Gson().fromJson(getIntent().getStringExtra(DATA), MetaData.class);
            setTitle(metaData.getTitle());
            orderDetail.setId(metaData.getId());
            orderDetail.setTitle(metaData.getTitle());
            orderDetail.setCreatedAt(metaData.getCreatedAt());
            orderDetail.setCreatedBy(metaData.getCreatedBy());
            orderDetail.setStatus(metaData.getStatus());
            orderDetail.setEbayId(metaData.getEbayId());
            orderDetail.setNotice(metaData.getNotice());
        }

        detail = orderDetail.getEbayId() + " \n " + orderDetail.getTitle();
        snackbar = Snackbar
                .make(findViewById(R.id.parent), detail, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);


        onCommingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {



            }
        };
        intentFilter = new IntentFilter(COMMING_MESSAGE);
        registerReceiver(onCommingMessage, intentFilter);


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onCommingMessage != null) {
            unregisterReceiver(onCommingMessage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {

        } else if (id == R.id.action_detail) {
            if (snackbar.isShown()) snackbar.dismiss();
            else
                snackbar.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
