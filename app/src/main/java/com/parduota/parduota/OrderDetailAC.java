package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

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

        String type = getIntent().getStringExtra(TYPE);
        orderDetail = new Datum();

        lvList = findViewById(R.id.lv_list);

        if (type.equals(TYPE_ORDER_LIST)) {
            Datum datum = new Gson().fromJson(getIntent().getStringExtra(DATA), Datum.class);
            setTitle(datum.getTitle());
            orderDetail.setId(datum.getId());
            orderDetail.setTitle(datum.getTitle());
            orderDetail.setCreatedAt(datum.getCreatedAt());
            orderDetail.setCreatedBy(datum.getCreatedBy());
            orderDetail.setStatus(datum.getStatus());
            orderDetail.setEbayId(datum.getEbayId());
            orderDetail.setNotice(datum.getNotice());

        } else if (type.equals(NOTIFICATION_SCREEN)) {
            MetaData metaData = new Gson().fromJson(getIntent().getStringExtra(DATA), MetaData.class);
            setTitle(metaData.getTitle());
            orderDetail.setId(metaData.getOrder_id());
            orderDetail.setTitle(metaData.getTitle());
            orderDetail.setCreatedAt(metaData.getCreatedAt());
            orderDetail.setCreatedBy(metaData.getCreatedBy());
            orderDetail.setStatus(metaData.getStatus());
            orderDetail.setEbayId(metaData.getEbayId());
            orderDetail.setNotice(metaData.getNotice());


            Ion.with(this).load(Constant.URL_GET_ORDER_DETAIL + orderDetail.getId()).setHeader(ION.authHeader(token)).as(OrderResponse.class).setCallback(new FutureCallback<OrderResponse>() {
                @Override
                public void onCompleted(Exception e, OrderResponse result) {

                    try {
                        orderDetail.setTitle(result.getOrder().getTitle());
                        orderDetail.setNotice(result.getOrder().getNotice());
                        orderDetail.setEbayId(result.getOrder().getEbayId());

                    } catch (Exception ignored) {

                    }

                }
            });
        }

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


        Ion.with(this).load(ION.URL_GET_MESSAGE + orderDetail.getId()).setHeader(ION.authHeader(token)).as(new TypeToken<List<MessageResponse>>() {
        }).setCallback(new FutureCallback<List<MessageResponse>>() {
            @Override
            public void onCompleted(Exception e, List<MessageResponse> result) {
                hideLoading();
                messageResponses.addAll(result);
                messageAdapter = new MessageAdapter(OrderDetailAC.this, messageResponses);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailAC.this);
                lvList.setLayoutManager(linearLayoutManager);
                lvList.setAdapter(messageAdapter);
                linearLayoutManager.scrollToPosition(messageResponses.size() - 1);
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
                Ion.with(getApplicationContext()).load(ION.URL_ADD_MESSAGE + orderDetail.getId()).addHeader("Authorization", "Bearer" + " " + token).setMultipartParameter("message", text).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        et_chat.setText("");

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
                                linearLayoutManager.scrollToPosition(messageResponses.size() - 1);
                            }
                        });
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

            Intent intent = new Intent(this, ExtendOrderDetailActivity.class);

            intent.putExtra(TITLE, orderDetail.getTitle());
            intent.putExtra(LINK, orderDetail.getEbayId());
            intent.putExtra(DESCRIPTION, orderDetail.getNotice());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
