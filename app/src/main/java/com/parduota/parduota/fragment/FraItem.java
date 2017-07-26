package com.parduota.parduota.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.LoginActivity;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.item.Datum;
import com.parduota.parduota.model.item.Item;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;
import com.parduota.parduota.view.MyDivider;

import java.util.ArrayList;

/**
 * Created by huy_quynh on 7/18/17.
 */

public class FraItem extends MFragment implements FutureCallback<Item>, Constant {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Datum> items;
    private ItemAdapter itemAdap;
    private String token;
    private int page = 1;

    private View progress_bar;

    private View view;

    private SwipeRefreshLayout swipeRefreshLayout;
    private FutureCallback futureCallback;

    @Override
    protected int setLayoutId() {
        return R.layout.fra_item;
    }

    @Override
    protected void initView(View view) {
        futureCallback = this;

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        token = SharePrefManager.getInstance(getActivity()).getAccessToken();
        recyclerView = (RecyclerView) view.findViewById(R.id.lv_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        items = new ArrayList<>();
        itemAdap = new ItemAdapter(getActivity(), items);
        recyclerView.setAdapter(itemAdap);
        progress_bar = view.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        ION.getData(getActivity(), getUrl(page), Item.class, this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                ION.getData(getActivity(), getUrl(page), Item.class, new FutureCallback() {

                    @Override
                    public void onCompleted(Exception e, Object result) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (e != null) {
                            return;
                        }
                        Item item = (Item) result;
                        if (item.getError() != null) {
                            if (item.getError().equals(TOKEN_EXPIRED)) {
                                showToast(getString(R.string.notify_out_of_session));
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                SharePrefManager.getInstance(getActivity()).removeAll();
                                getActivity().finish();
                                return;
                            }
                        }
                        Log.e("DATA", new Gson().toJson(result));
                        if (result != null) {
                            if (item.getData() != null) {
                                if (items.size() > 0) items = new ArrayList<>();
                                items.addAll(item.getData());
                                itemAdap.notifyDataSetChanged();
                                Intent intent = new Intent(UPDATE_TOTAL);
                                intent.putExtra(DATA, item.getData().size() + "");
                                getActivity().sendBroadcast(intent);
                            }
                        }
                    }
                });
            }
        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page++;
                ION.getData(getActivity(), getUrl(page), Item.class, futureCallback);
            }
        });
        recyclerView.addItemDecoration(new MyDivider(getActivity()));
    }

    @Override
    protected void setData(View view) {

    }

    public String getUrl(int page) {
        String url = Constant.URL_ALL_ITEM + "&token=" + token + "&page=" + page;
        Log.e("URL", url);
        return url;
    }

    @Override
    public void onCompleted(Exception e, Item result) {
        progress_bar.setVisibility(View.GONE);
        if (e != null) {
            return;
        }
        if (result.getError() != null) {
            if (result.getError().equals(TOKEN_EXPIRED)) {
                showToast(getString(R.string.notify_out_of_session));
                startActivity(new Intent(getActivity(), LoginActivity.class));
                SharePrefManager.getInstance(getActivity()).removeAll();
                getActivity().finish();
                return;
            }
        }

        Log.e("DATA", new Gson().toJson(result));
        if (result != null) {
            if (result.getData() != null & result.getData().size() > 0) {
                if (result.getData().size() < 5) {
                    recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                        }
                    });
                }
                for (int i = 0; i < result.getData().size(); i++) {
                    switch (result.getData().get(i).getStatus()) {
                        case DRAFT:
                            result.getData().get(i).setStatus_text(getString(R.string.draft));
                            result.getData().get(i).setColor_status(getResources().getColor(R.color.draft));
                            break;
                        case PENDING:
                            result.getData().get(i).setStatus_text(getString(R.string.pending));
                            result.getData().get(i).setColor_status(getResources().getColor(R.color.pending));
                            break;
                        case SOLD:
                            result.getData().get(i).setStatus_text(getString(R.string.sold));
                            result.getData().get(i).setColor_status(getResources().getColor(R.color.sold));
                            break;
                        case REJECT:
                            result.getData().get(i).setStatus_text(getString(R.string.reject));
                            result.getData().get(i).setColor_status(getResources().getColor(R.color.reject));
                            break;

                    }
                }
                items.addAll(result.getData());
                itemAdap.notifyDataSetChanged();
                Intent intent = new Intent(UPDATE_TOTAL);
                intent.putExtra(DATA, result.getData().size() + "");
                getActivity().sendBroadcast(intent);
            }
        }
    }

    public static Fragment instance(int position) {
        FraItem fraItem = new FraItem();
        Bundle bundle = new Bundle();
        bundle.putInt(DATA, position);
        fraItem.setArguments(bundle);
        return fraItem;
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        public Context context;
        public ArrayList<Datum> items;

        public ItemAdapter(Context context, ArrayList<Datum> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Datum datum = items.get(position);
            holder.datum = datum;
            holder.tv_title.setText(datum.getTitle());
            holder.tv_price.setText(getString(R.string.hint_price) + ": " + datum.getPrice());
            holder.tv_quality.setText(datum.getQuantity() + "");
            holder.tv_time.setText(datum.getCreatedAt());
            holder.tv_status.setText(datum.getStatus_text());
            holder.tv_status.setBackgroundColor(datum.getColor_status());
            Glide.with(context).load(PHOTO_URL + datum.getMedia().get(0).getLink()).into(holder.img_avatar);

            holder.itemView.setOnClickListener(holder.onClickListener);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

}