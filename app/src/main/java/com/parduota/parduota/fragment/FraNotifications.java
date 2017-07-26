package com.parduota.parduota.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.notify.Datum;
import com.parduota.parduota.model.notify.Notify;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 6/13/17.
 */

public class FraNotifications extends MFragment implements FutureCallback<Notify> {


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private NotifyAdapter notifyAdapter;
    private ArrayList<Datum> notifies;
    private String token;
    private int page = 1;
    private View loading;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int setLayoutId() {
        return R.layout.fra_notification;
    }

    @Override
    protected void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        token = SharePrefManager.getInstance(getActivity()).getAccessToken();
        recyclerView = (RecyclerView) view.findViewById(R.id.lv_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        notifies = new ArrayList<>();
        notifyAdapter = new NotifyAdapter(getActivity(), notifies);
        recyclerView.setAdapter(notifyAdapter);
        loading = view.findViewById(R.id.progress_bar);
        loading.setVisibility(View.VISIBLE);

        ION.getData(getActivity(), getUrl(page), Notify.class, this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                ION.getData(getActivity(), getUrl(page), Notify.class, new FutureCallback() {
                    @Override
                    public void onCompleted(Exception e, Object result) {
                        swipeRefreshLayout.setRefreshing(false);
                        Notify notify = (Notify) result;
                        loading.setVisibility(View.GONE);
                        if (e != null) {
                            return;
                        }
                        if (notify != null) {
                            if (notify.getData() != null) {
                                if (notifies.size() > 0) notifies = new ArrayList<>();
                                notifies.addAll(notify.getData());
                                notifyAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void setData(View view) {

    }


    public String getUrl(int page) {
        String url = Constant.URL_GET_NOTIFY + "/?token=" + token + "&page=" + page;
        Log.e("URL", url);
        return url;
    }

    @Override
    public void onCompleted(Exception e, Notify result) {
        loading.setVisibility(View.GONE);
        if (e != null) {

            return;
        }
        if (result != null) {
            if (result.getData() != null) {
                notifies.addAll(result.getData());
                notifyAdapter.notifyDataSetChanged();
            }

        }

    }


    private class NotifyAdapter extends RecyclerView.Adapter<NotifyHolder> {

        private Context context;
        private ArrayList<Datum> notifies;

        public NotifyAdapter(Context context,
                             ArrayList<Datum> notifies) {
            this.context = context;
            this.notifies = notifies;

        }

        @Override
        public NotifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notify_item, parent, false);
            return new NotifyHolder(view);
        }

        @Override
        public void onBindViewHolder(NotifyHolder holder, int position) {
            Datum datum = notifies.get(position);
            holder.tv_title.setText(Html.fromHtml(datum.getText()));
            holder.tv_time.setText(datum.getCreatedAt());
        }

        @Override
        public int getItemCount() {
            return notifies.size();
        }
    }
}
