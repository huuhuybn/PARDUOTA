package com.parduota.parduota.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by huy_quynh on 7/18/17.
 */

public class FraItem extends MFragment implements FutureCallback<Item>, Constant {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Datum> items;
    private ItemAdapter itemAdap;
    private String token;

    private View progress_bar;

    private View view;

    private View no_item;

    //private SwipeRefreshLayout swipeRefreshLayout;
    private FutureCallback futureCallback;


    public static FraItem newInstance(int idItem) {

        FraItem fraItem = new FraItem();

        Bundle bundle = new Bundle();
        bundle.putInt(DATA, idItem);
        fraItem.setArguments(bundle);

        return fraItem;
    }


    @Override
    protected int setLayoutId() {
        return R.layout.fra_item;
    }

    @Override
    protected void initView(View view) {

        int TYPE = Objects.requireNonNull(getArguments()).getInt(DATA);

        futureCallback = this;

        no_item = view.findViewById(R.id.tv_no_item);

        //swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh);
        token = SharePrefManager.getInstance(getActivity()).getAccessToken();
        recyclerView = view.findViewById(R.id.lv_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        items = new ArrayList<>();
        itemAdap = new ItemAdapter(getActivity(), items);
        recyclerView.setAdapter(itemAdap);
        progress_bar = view.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);


        getDataWithType(TYPE);


    /*    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                            showToast(getString(R.string.notify_out_of_session));
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            SharePrefManager.getInstance(getActivity()).removeAll();
                            getActivity().finish();
                            return;
                        }

                        Log.e("DATA", new Gson().toJson(result));
                        if (result != null) {
                            if (item.getData() != null) {
                                if (items.size() > 0) items = new ArrayList<>();
                                items.addAll(item.getData());
                                itemAdap.notifyDataSetChanged();
                            }
                        }
                    }
                });
            }
        });*/

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //page++;
                //ION.getData(getActivity(), getUrl(page), Item.class, futureCallback);
            }
        });
        //recyclerView.addItemDecoration(new MyDivider(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.UPDATE_SUCCESSFUL) {

        }
    }

    @Override
    protected void setData(View view) {

    }

    private void getDataWithType(int type) {

        int page = 1;
        ION.getDataWithToken(getActivity(), token, getItemByStatus(type, page), Item.class, futureCallback);
    }


    private String getItemByStatus(int type, int page) {
        String url = Constant.URL_GET_ITEM_BY_STATUS + type + "?page=" + page;
        Log.e("getItemByStatus", url);
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
                Objects.requireNonNull(getActivity()).finish();
                return;
            }
        }
        //Log.e("DATA", new Gson().toJson(result));
        if (result.getData() != null & result.getData().size() > 0) {

            Collections.reverse(result.getData());
            no_item.setVisibility(View.GONE);
            if (result.getData().size() < 5) {
                recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                    }
                });
            }
         /*   for (int i = 0; i < result.getData().size(); i++) {
                switch (result.getData().get(i).getStatus()) {
                    case DRAFT:
                        result.getData().get(i).setStatusText(getString(R.string.draft));
                        result.getData().get(i).setColorStatus(getResources().getColor(R.color.draft));
                        break;
                    case PENDING:
                        result.getData().get(i).setStatusText(getString(R.string.pending));
                        result.getData().get(i).setColorStatus(getResources().getColor(R.color.pending));
                        break;
                    case SOLD:
                        result.getData().get(i).setStatusText(getString(R.string.sold));
                        result.getData().get(i).setColorStatus(getResources().getColor(R.color.sold));
                        break;
                    case REJECT:
                        result.getData().get(i).setStatusText(getString(R.string.reject));
                        result.getData().get(i).setColorStatus(getResources().getColor(R.color.reject));
                        break;

                }
            }*/
            items.addAll(result.getData());
            itemAdap.notifyDataSetChanged();
        } else no_item.setVisibility(View.VISIBLE);
    }

    public static Fragment instance(int position) {
        FraItem fraItem = new FraItem();
        Bundle bundle = new Bundle();
        bundle.putInt(DATA, position);
        fraItem.setArguments(bundle);
        return fraItem;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        final Context context;
        final ArrayList<Datum> items;

        ItemAdapter(Context context, ArrayList<Datum> items) {
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
            holder.tv_quality.setText(context.getString(R.string.hint_quantity) + ": " + datum.getQuantity() + "");
            holder.tv_time.setText(datum.getCreatedAt());
            holder.tv_status.setText("" + datum.getStatus());
            holder.tv_status.setBackgroundColor(datum.getColorStatus());

            try {

                Glide.with(context).load(PHOTO_URL + datum.getMedia().get(0).getLink()).into(holder.img_avatar);

            } catch (Exception ignored) {

            }

            holder.itemView.setOnClickListener(holder.onClickListener);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

}
