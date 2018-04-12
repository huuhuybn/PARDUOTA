package com.parduota.parduota;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.BaseActivity;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.face.OnNotificationClick;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.item.Category;
import com.parduota.parduota.model.item.Medium;
import com.parduota.parduota.model.item.User;
import com.parduota.parduota.model.notification.Datum;
import com.parduota.parduota.model.notification.MetaData;
import com.parduota.parduota.model.notification.Notification;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NotificationAC extends BaseActivity implements FutureCallback, Constant {

    String token;
    int page_ = 1;

    private FutureCallback<Notification> notificationFutureCallback;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView lvList;
    private NotificationAdapter notificationAdapter;
    private ArrayList<Datum> data;
    private OnNotificationClick onNotificationClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationFutureCallback = this;

        token = SharePrefManager.getInstance(this).getAccessToken();

        onNotificationClick = new OnNotificationClick() {
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
        lvList = findViewById(R.id.lv_list);
        data = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        notificationAdapter = new NotificationAdapter(this, data, onNotificationClick);
        lvList.setAdapter(notificationAdapter);
        lvList.setLayoutManager(linearLayoutManager);

//        lvList.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                page_++;
//                ION.getDataWithToken(getApplicationContext(), token, Constant.URL_GET_NOTIFICATION + page, Notification.class, notificationFutureCallback);
//            }
//        });


        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(1).setChecked(true);
    }


    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
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

        if (notification != null) {
            Log.e("notification", new Gson().toJson(notification));
            if (notification.getData() != null)
                data.addAll(notification.getData());
            notificationAdapter.notifyDataSetChanged();
        }

    }

    public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> implements Constant {

        public Context context;
        public ArrayList<Datum> datas;
        public OnNotificationClick onNotificationClick;

        public NotificationAdapter(Context context, ArrayList<Datum> datas, OnNotificationClick onNotificationClick) {
            this.context = context;
            this.datas = datas;
            this.onNotificationClick = onNotificationClick;
        }

        @Override
        public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
            return new NotificationHolder(view);
        }

        @Override
        public void onBindViewHolder(NotificationHolder holder, int position) {

            final Datum datum = datas.get(position);
            holder.tvTitle.setText(Html.fromHtml(datum.getText()));
            holder.tvDateTime.setText(datum.getCreatedAt());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNotificationClick.onClick(datum);
                }
            });
            if (datum.getReaded() == READED) {

                holder.tvTitle.setTextColor(getResources().getColor(R.color.grey_strong));
                holder.tvDateTime.setTextColor(getResources().getColor(R.color.grey_strong));

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

        public NotificationHolder(View convertView) {

            super(convertView);

            tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            tvDateTime = (TextView) convertView.findViewById(R.id.tv_date_time);

        }
    }
}
