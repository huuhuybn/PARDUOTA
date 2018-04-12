package com.parduota.parduota;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.BaseActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.createorder.Order;
import com.parduota.parduota.model.createorder.OrderResponse;
import com.parduota.parduota.model.order.Datum;

/**
 * Created by MAC2015 on 2/9/18.
 */

public class AddOrderAC extends BaseActivity {

    private EditText etTitle;
    private EditText etEbayId;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_order);

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

                if (des.matches("")) {
                    etDescription.setError(getString(R.string.notify_input));
                    break;
                }


                String token = sharePrefManager.getAccessToken();

                ION.postDataWithToken(this, Constant.URL_CREATE_ORDER, token, ION.createOrder(title, ebayId, des), OrderResponse.class, this);


                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        if (result != null) {
            OrderResponse orderResponse = (OrderResponse) result;

            if (orderResponse.getStatus().equals("ok")) {
                Toast.makeText(this, getString(R.string.notify_order_succseccful), Toast.LENGTH_SHORT).show();
                Datum datum = new Datum();
                datum.setId(orderResponse.getOrder().getId());
                datum.setTitle(orderResponse.getOrder().getTitle());
                datum.setNotice(orderResponse.getOrder().getNotice());
                datum.setCreatedAt(orderResponse.getOrder().getCreatedAt());
                datum.setUpdatedAt(orderResponse.getOrder().getUpdatedAt());
                Intent intent = new Intent(this, OrderDetailAC.class);
                intent.putExtra(DATA, new Gson().toJson(datum));
                intent.putExtra(TYPE, TYPE_ORDER_LIST);
                startActivity(intent);
                finish();

            }
        }
    }
}
