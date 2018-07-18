package com.parduota.parduota;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.ChargerAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.charger.Charge;
import com.parduota.parduota.model.charger.Datum;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ChargeAC extends MActivity implements Constant, Callback<Charge> {


    private String token;

    private RecyclerView lvList;
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;

    private ChargerAdapter chargerAdapter;

    private List<Datum> datas;

    private Callback<Charge> chargeCallback;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_charger;
    }

    @Override
    protected void initView() {


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        token = sharePrefManager.getAccessToken();
        chargeCallback = this;

        lvList = findViewById(R.id.lv_list);
        linearLayoutManager = new LinearLayoutManager(this);

        datas = new ArrayList<>();
        chargerAdapter = new ChargerAdapter(this, datas);

        showLoading();
        RetrofitRequest apiService =
                RetrofitClient.getClient().create(RetrofitRequest.class);

        apiService.getChargeList(RetrofitRequest.PRE_TOKEN + token, page).enqueue(this);

        lvList.setLayoutManager(linearLayoutManager);
        lvList.setAdapter(chargerAdapter);
    }

    @Override
    public void onResponse(Call<Charge> call, Response<Charge> response) {
        hideLoading();

        if (response.body() != null) {
            Charge charger = response.body();

            if (Constant.isDEBUG) Log.e("SIZE-CHARGE", charger.getData().size() + "");

            datas.addAll(charger.getData());
            chargerAdapter.notifyDataSetChanged();
            if (charger.getData().size() < 10) {
                lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                    }
                });
            } else if (charger.getData().size() == 0) {
                try {

                    findViewById(R.id.tv_no_item).setVisibility(View.VISIBLE);
                    lvList.setVisibility(View.GONE);
                    lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        @Override
                        public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {


                        }
                    });

                } catch (Exception ignored) {

                }
            } else {
                lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                        page++;
                        if (Constant.isDEBUG) Log.e("PAGE", page + "");

                        RetrofitRequest apiService =
                                RetrofitClient.getClient().create(RetrofitRequest.class);

                        apiService.getChargeList(RetrofitRequest.PRE_TOKEN + token, page).enqueue(chargeCallback);

                    }
                });
            }

        }
    }

    @Override
    public void onFailure(Call<Charge> call, Throwable t) {

    }
}
