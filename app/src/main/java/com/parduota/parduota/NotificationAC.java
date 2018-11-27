package com.parduota.parduota;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.face.OnNotificationClick;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.notification.Datum;
import com.parduota.parduota.model.notification.Notification;
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
public class NotificationAC extends MActivity implements Constant {

    private String token;
    private int page_ = 1;

    private NotificationAdapter notificationAdapter;
    private ArrayList<Datum> data;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_notification;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initView() {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getString(R.string.title_activity_notification_ac));

        token = SharePrefManager.getInstance(this).getAccessToken();


        OnNotificationClick onNotificationClick = new OnNotificationClick() {
            @Override
            public void onClick(Datum datum) {

                Log.e("TYPE", datum.getType() + "");
                Log.e("ID",datum.getId()+"");

                switch (datum.getType()) {

                    case TYPE_REJECT_ITEM:
                        Intent intentReject = new Intent(getApplicationContext(), ItemDetailAC.class);
                        intentReject.putExtra(ID,datum.getMetaData().getId());
                        startActivity(intentReject);
                        break;

                    case TYPE_UPDATE_ITEM:
                        Intent intent = new Intent(getApplicationContext(), ItemDetailAC.class);
                        intent.putExtra(ID,datum.getMetaData().getId());
                        startActivity(intent);
                        break;

                    case TYPE_MESSAGE_ORDER:
                        Intent intent_ = new Intent(getApplicationContext(), OrderDetailAC.class);
                        intent_.putExtra(ID, datum.getMetaData().getOrder_id());
                        startActivity(intent_);
                        break;

                    case TYPE_CHARGE:
                        Intent intentCharge = new Intent(getApplicationContext(), ChargeAC.class);
                        startActivity(intentCharge);
                        break;

                    case TYPE_CLOSED_ORDER:
                        Intent intentCloseOrder = new Intent(getApplicationContext(), OrderDetailAC.class);
                        intentCloseOrder.putExtra(ID, datum.getMetaData().getId());
                        startActivity(intentCloseOrder);
                        break;

                    case TYPE_ACTIVE_ITEM:
                        Intent intentActiveItem = new Intent(getApplicationContext(), ItemDetailAC.class);
                        intentActiveItem.putExtra(ID,datum.getMetaData().getId());
                        startActivity(intentActiveItem);
                        break;

                }
            }
        };


        showLoading();
        getNotification(page_);

        RecyclerView lvList = findViewById(R.id.lv_list);
        data = new ArrayList<>();
        
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notificationAdapter = new NotificationAdapter(this, data, onNotificationClick);
        lvList.setAdapter(notificationAdapter);
        lvList.setLayoutManager(linearLayoutManager);

        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page_++;
                getNotification(page_);
            }
        });


    }

    public void getNotification(int page) {
        hideLoading();
        RetrofitRequest apiService =
                RetrofitClient.getClient(NotificationAC.this).create(RetrofitRequest.class);

        String locale = sharePrefManager.getLocale();

        Log.e("locale", locale);

        Call<Notification> call = apiService.getNotification(RetrofitRequest.PRE_TOKEN + token, page, locale);
        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {

                if (response.body().getError() != null) {
                    if (response.body().getError().equals(TOKEN_EXPIRED)) {
                        showToast(getString(R.string.notify_out_of_session));
                        startActivity(new Intent(NotificationAC.this, LoginActivity.class));
                        SharePrefManager.getInstance(NotificationAC.this).removeAll();
                        finish();
                        return;
                    }
                }

                if (response.body().getData() != null)
                    data.addAll(response.body().getData());
                notificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });
    }

    class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> implements Constant {

        final Context context;
        final ArrayList<Datum> datas;
        final OnNotificationClick onNotificationClick;

        final String token_;

        NotificationAdapter(Context context, ArrayList<Datum> datas, OnNotificationClick onNotificationClick) {
            this.context = context;
            this.datas = datas;
            this.onNotificationClick = onNotificationClick;
            this.token_ = SharePrefManager.getInstance(context).getAccessToken();
        }

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
            return new NotificationHolder(view);
        }

        @Override
        public void onBindViewHolder(final NotificationHolder holder, int position) {

            final Datum datum = datas.get(holder.getAdapterPosition());
            holder.tvTitle.setText(Html.fromHtml(datum.getText()));
            holder.tvDateTime.setText(datum.getCreatedAt());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNotificationClick.onClick(datum);

                    datas.get(holder.getAdapterPosition()).setReaded(1);
                    notifyDataSetChanged();

                    Ion.with(context).load(Constant.URL_UPDATE_READ + datum.getId().toString()).setHeader(ION.authHeader(token_)).setBodyParameter("", "").asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            sharePrefManager.subCountNotification();
                        }
                    });
                }
            });
            if (datum.getReaded() != READED) {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.grey_strong));
            } else {
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }


    }

    private class NotificationHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle;
        final TextView tvDateTime;

        NotificationHolder(View convertView) {

            super(convertView);

            tvTitle = convertView.findViewById(R.id.tv_title);
            tvDateTime = convertView.findViewById(R.id.tv_date_time);

        }
    }
}
