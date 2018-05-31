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
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NotificationAC extends MActivity implements FutureCallback, Constant {

    private String token;
    private int page_ = 1;

    private FutureCallback<Notification> notificationFutureCallback;

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

        notificationFutureCallback = this;

        token = SharePrefManager.getInstance(this).getAccessToken();

        OnNotificationClick onNotificationClick = new OnNotificationClick() {
            @Override
            public void onClick(Datum datum) {

                switch (datum.getTypeDesc()) {
                    case TYPE_UPDATE_ITEM:
                        Intent intent = new Intent(getApplicationContext(), ItemDetailAC.class);
                        intent.putExtra(FROM, NOTIFICATION_SCREEN);
                        intent.putExtra(DATA, new Gson().toJson((datum.getMetaData())));
                        startActivity(intent);
                        break;
                    case TYPE_MESSAGE_ORDER:
                        Intent intent_ = new Intent(getApplicationContext(), OrderDetailAC.class);
                        intent_.putExtra(TYPE, NOTIFICATION_SCREEN);
                        intent_.putExtra(DATA, new Gson().toJson((datum.getMetaData())));
                        startActivity(intent_);
                        break;
                }
            }
        };
        ION.getDataWithToken(getApplicationContext(), token, Constant.URL_GET_NOTIFICATION + page_, Notification.class, notificationFutureCallback);
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
                ION.getDataWithToken(getApplicationContext(), token, Constant.URL_GET_NOTIFICATION + page, Notification.class, notificationFutureCallback);
            }
        });


    }


    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        if (Constant.isDEBUG) Log.e("DATA",new Gson().toJson(result));
        Notification notification = (Notification) result;

        if (notification.getError() != null) {
            if (notification.getError().equals(TOKEN_EXPIRED)) {
                showToast(getString(R.string.notify_out_of_session));
                startActivity(new Intent(this, LoginActivity.class));
                SharePrefManager.getInstance(this).removeAll();
                finish();
                return;
            }
        }

        Log.e("notification", new Gson().toJson(notification));
        if (notification.getData() != null)
            data.addAll(notification.getData());
        notificationAdapter.notifyDataSetChanged();

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
        public void onBindViewHolder(final NotificationHolder holder,int position) {

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
