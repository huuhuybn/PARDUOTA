package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.MessageAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.MessageResponse;
import com.parduota.parduota.model.createorder.OrderResponse;
import com.parduota.parduota.model.notification.MetaData;
import com.parduota.parduota.model.order.Datum;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.view.OrderDetailDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class OrderDetailAC extends MActivity implements Constant, FutureCallback {


    private Datum orderDetail;

    private BroadcastReceiver onCommingMessage;

    private RecyclerView lvList;
    private String token;


    private EditText et_chat;

    private List<MessageResponse> messageResponses;

    private int orderID = -1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_detail;
    }

    private MessageAdapter messageAdapter;

    @Override
    protected void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        messageResponses = new ArrayList<>();
        et_chat = findViewById(R.id.et_chat);
        Button btn_send = findViewById(R.id.btn_send);

        token = sharePrefManager.getAccessToken();


        lvList = findViewById(R.id.lv_list);

        orderDetail = new Datum();

        orderID = getIntent().getIntExtra(ID, -1);
        orderDetail.setId(orderID);

        Ion.with(this).load(Constant.URL_GET_ORDER_DETAIL + orderDetail.getId()).setHeader(ION.authHeader(token)).as(OrderResponse.class).setCallback(new FutureCallback<OrderResponse>() {
            @Override
            public void onCompleted(Exception e, OrderResponse result) {

                try {
                    orderDetail.setTitle(result.getOrder().getTitle());
                    setTitle(result.getOrder().getTitle());
                    orderDetail.setNotice(result.getOrder().getNotice());
                    orderDetail.setEbayId(result.getOrder().getEbayId());

                } catch (Exception ignored) {

                }

            }
        });


        onCommingMessage = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Ion.with(getApplicationContext()).load(ION.URL_GET_MESSAGE + orderDetail.getId()).addHeader("Authorization", "Bearer" + " " + token).as(new TypeToken<List<MessageResponse>>() {
                }).setCallback(new FutureCallback<List<MessageResponse>>() {
                    @Override
                    public void onCompleted(Exception e, List<MessageResponse> result) {
                        hideLoading();
                        //Log.e("A", result.get(0).getMessages());
                        messageResponses.clear();
                        messageResponses.addAll(result);
                        messageAdapter = new MessageAdapter(OrderDetailAC.this, messageResponses);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailAC.this);
                        lvList.setLayoutManager(linearLayoutManager);
                        lvList.setAdapter(messageAdapter);
                        lvList.scrollToPosition(messageResponses.size() - 1);
                    }
                });
            }
        };


        IntentFilter intentFilter = new IntentFilter(COMMING_MESSAGE);
        registerReceiver(onCommingMessage, intentFilter);

        showLoading();


        RetrofitRequest apiService =
                RetrofitClient.getClient().create(RetrofitRequest.class);

        Call<List<MessageResponse>> call = apiService.getOrderMessage(RetrofitRequest.PRE_TOKEN + token, orderID);
        call.enqueue(new Callback<List<MessageResponse>>() {
            @Override
            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                hideLoading();
                try {

                    messageResponses.addAll(response.body());
                    messageAdapter = new MessageAdapter(OrderDetailAC.this, messageResponses);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailAC.this);
                    lvList.setLayoutManager(linearLayoutManager);
                    lvList.setAdapter(messageAdapter);
                    linearLayoutManager.scrollToPosition(messageResponses.size() - 1);

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                hideLoading();
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = et_chat.getText().toString().trim();
                if (text.matches("")) {
                    et_chat.setError(getString(R.string.notify_empty));
                    return;
                }
                showLoading();

                RetrofitRequest apiService =
                        RetrofitClient.getClient().create(RetrofitRequest.class);

                Call<JsonObject> call_ = apiService.sendOrderMessage(RetrofitRequest.PRE_TOKEN + token, orderDetail.getId(), text);
                call_.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        et_chat.setText("");

                        RetrofitRequest apiService =
                                RetrofitClient.getClient().create(RetrofitRequest.class);

                        Call<List<MessageResponse>> callNew = apiService.getOrderMessage(RetrofitRequest.PRE_TOKEN + token, orderDetail.getId());

                        callNew.enqueue(new Callback<List<MessageResponse>>() {
                            @Override
                            public void onResponse(Call<List<MessageResponse>> call, Response<List<MessageResponse>> response) {
                                hideLoading();
                                try {

                                    hideLoading();
                                    messageResponses.clear();
                                    messageResponses.addAll(response.body());
                                    messageAdapter = new MessageAdapter(OrderDetailAC.this, messageResponses);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailAC.this);
                                    lvList.setLayoutManager(linearLayoutManager);
                                    lvList.setAdapter(messageAdapter);
                                    linearLayoutManager.scrollToPosition(messageResponses.size() - 1);

                                } catch (Exception e) {

                                }

                            }

                            @Override
                            public void onFailure(Call<List<MessageResponse>> call, Throwable t) {
                                hideLoading();
                                if (Constant.isDEBUG) Log.e("Message", t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        hideLoading();

                    }
                });
            }
        });


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

            Intent intent = new Intent();
            intent.putExtra(TITLE, orderDetail.getTitle());
            intent.putExtra(LINK, orderDetail.getEbayId());
            intent.putExtra(DESCRIPTION, orderDetail.getNotice());
            FragmentManager fm = getSupportFragmentManager();
            OrderDetailDialog orderDetailDialog = OrderDetailDialog.instance(intent);
            // User must accept this dialog to go
            orderDetailDialog.show(fm, "orderDetailDialog");

        }
        return super.onOptionsItemSelected(item);
    }
}
