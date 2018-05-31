package com.parduota.parduota;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.OrderAdapter;
import com.parduota.parduota.face.OnOrderClick;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.model.order.Order;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OrderAC extends MActivity implements Constant, FutureCallback {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_ac;
    }

    private OrderAdapter orderAdapter;
    private ArrayList<Datum> orders;

    private FutureCallback<Order> orderFutureCallback;

    private String token;
    private int page_ = 1;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton btnAddOrder = findViewById(R.id.fab);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startNewActivity(AddOrderAC.class);

            }
        });
        orderFutureCallback = this;

        token = SharePrefManager.getInstance(this).getAccessToken();
        showLoading();
        ION.getDataWithToken(this, token, URL_GET_ORDER + page_, Order.class, this);

        orderFutureCallback = this;
        RecyclerView lv_list = findViewById(R.id.lv_list);
        orders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        OnOrderClick orderOnItemClick = new OnOrderClick() {
            @Override
            public void onClick(Datum order) {
                Intent intent = new Intent(OrderAC.this, OrderDetailAC.class);
                intent.putExtra(TYPE, TYPE_ORDER_LIST);
                intent.putExtra(DATA, new Gson().toJson(order));
                startActivity(intent);
            }
        };

        orderAdapter = new OrderAdapter(this, orders, orderOnItemClick);
        lv_list.setAdapter(orderAdapter);
        lv_list.setLayoutManager(linearLayoutManager);
        lv_list.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page_++;
                ION.getDataWithToken(getApplicationContext(), token, URL_GET_ORDER + page_, Order.class, orderFutureCallback);
            }
        });

    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        hideLoading();


        if (result != null) {
            Order order = (Order) result;
            Log.e("AAA", new Gson().toJson(order));


            if (order.getData() != null) {
                orders.addAll(order.getData());
                orderAdapter.notifyDataSetChanged();
            }

        }

    }
}
