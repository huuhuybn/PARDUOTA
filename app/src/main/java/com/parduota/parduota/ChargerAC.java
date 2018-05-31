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
import com.parduota.parduota.model.charger.Charger;
import com.parduota.parduota.model.charger.Datum;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ChargerAC extends MActivity implements FutureCallback, Constant {


    private String token;

    private RecyclerView lvList;
    private LinearLayoutManager linearLayoutManager;
    private int page = 0;

    private FutureCallback futureCallback;
    private ChargerAdapter chargerAdapter;

    private List<Datum> datas;

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

        lvList = findViewById(R.id.lv_list);
        linearLayoutManager = new LinearLayoutManager(this);
        futureCallback = this;

        datas = new ArrayList<>();
        chargerAdapter = new ChargerAdapter(this, datas);

        showLoading();
        ION.getDataWithToken(this, token, Constant.URL_GET_CHARGER_HISTORY + page, Charger.class, this);
        lvList.setLayoutManager(linearLayoutManager);
        lvList.setAdapter(chargerAdapter);
    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);

        hideLoading();

        if (result != null) {
            Charger charger = (Charger) result;

            Log.e("AAAAA", charger.getData().size() + " ");

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

                } catch (Exception ignored) {

                }
            } else {
                lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                        page++;
                        ION.getDataWithToken(ChargerAC.this, token, Constant.URL_GET_CHARGER_HISTORY + page, Charger.class, futureCallback);
                    }
                });
            }

        }

    }
}
