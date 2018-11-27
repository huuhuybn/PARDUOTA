package com.parduota.parduota;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.OrderAdapter;
import com.parduota.parduota.face.OnOrderClick;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.model.order.Order;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OrderAC extends MActivity implements Constant, Callback<Order> {

    public Callback<Order> orderCallback;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_ac;
    }

    private OrderAdapter orderAdapter;
    private ArrayList<Datum> orders;

    private String token;
    private int page_ = 1;

    private LinearLayout tvNoItem;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        tvNoItem = findViewById(R.id.tv_no_item);

        FloatingActionButton btnAddOrder = findViewById(R.id.fab);
        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startNewActivity(AddOrderAC.class);

            }
        });

        token = SharePrefManager.getInstance(this).getAccessToken();
        showLoading();

        RetrofitRequest apiService =
                RetrofitClient.getClient(OrderAC.this).create(RetrofitRequest.class);
        orderCallback = this;
        apiService.getOrderList(page_, RetrofitRequest.PRE_TOKEN + token).enqueue(this);
        RecyclerView lv_list = findViewById(R.id.lv_list);
        orders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        OnOrderClick orderOnItemClick = new OnOrderClick() {
            @Override
            public void onClick(Datum order) {
                Intent intent = new Intent(OrderAC.this, OrderDetailAC.class);
                intent.putExtra(ID, order.getId());
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
                RetrofitRequest apiService =
                        RetrofitClient.getClient(OrderAC.this).create(RetrofitRequest.class);
                apiService.getOrderList(page_, RetrofitRequest.PRE_TOKEN + token).enqueue(orderCallback);

            }
        });

    }

    @Override
    public void onResponse(Call<Order> call, Response<Order> response) {

        hideLoading();


        if (response.body() != null) {

            if (Constant.isDEBUG)
                Log.e("AAA", new Gson().toJson(response.body()));
            if (response.body().getData() != null) {

                if (response.body().getTotal() == 0) {
                    tvNoItem.setVisibility(View.VISIBLE);
                } else {
                    orders.addAll(response.body().getData());
                    orderAdapter.notifyDataSetChanged();
                }
            }

        }
    }

    @Override
    public void onFailure(Call<Order> call, Throwable t) {

    }
}
