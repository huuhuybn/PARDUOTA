package com.parduota.parduota;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.model.createorder.OrderResponse;
import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAC2015 on 2/9/18.
 */

public class AddOrderAC extends MActivity implements Callback<OrderResponse> {

    private EditText etTitle;
    private EditText etEbayId;
    private EditText etDescription;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_order;
    }

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        etTitle = findViewById(R.id.et_title);
        etEbayId = findViewById(R.id.et_ebay_id);
        etDescription = findViewById(R.id.et_description);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_order:

                String title = etTitle.getText().toString().trim();
                String ebayId = etEbayId.getText().toString().trim();
                String des = etDescription.getText().toString().trim();

                if (title.matches("")) {
                    etTitle.setError(getString(R.string.notify_input));
                    break;
                }

                if (ebayId.matches("")) {
                    etEbayId.setError(getString(R.string.notify_input));
                    break;
                }

                if (!URLUtil.isValidUrl(ebayId)) {
                    etEbayId.setError(getString(R.string.notify_input_ebay_link));
                    break;
                }

                if (des.matches("")) {
                    etDescription.setError(getString(R.string.notify_input));
                    break;
                }


                String token = sharePrefManager.getAccessToken();


                showLoading();
                RetrofitRequest apiService =
                        RetrofitClient.getClient(AddOrderAC.this).create(RetrofitRequest.class);


                apiService.addNewOrder(RetrofitRequest.PRE_TOKEN + token, title, ebayId, des).enqueue(this);


                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
        hideLoading();
        if (response != null) {
            if (response.body().getStatus().equals("ok")) {
                Toast.makeText(this, getString(R.string.notify_order_succseccful), Toast.LENGTH_SHORT).show();
                Datum datum = new Datum();
                datum.setId(response.body().getOrder().getId());
                datum.setTitle(response.body().getOrder().getTitle());
                datum.setNotice(response.body().getOrder().getNotice());
                datum.setCreatedAt(response.body().getOrder().getCreatedAt());
                datum.setUpdatedAt(response.body().getOrder().getUpdatedAt());
                Intent intent = new Intent(this, OrderDetailAC.class);
                intent.putExtra(DATA, new Gson().toJson(datum));
                intent.putExtra(TYPE, TYPE_ORDER_LIST);
                startActivity(intent);
                finish();

            }
        }
    }

    @Override
    public void onFailure(Call<OrderResponse> call, Throwable t) {

    }
}
