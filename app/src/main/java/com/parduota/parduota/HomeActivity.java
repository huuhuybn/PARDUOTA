package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.adapter.ItemAdapter;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;
import com.parduota.parduota.model.item.Datum;
import com.parduota.parduota.model.item.Item;
import com.parduota.parduota.utils.EndlessRecyclerViewScrollListener;
import com.parduota.parduota.utils.SharePrefManager;
import com.parduota.parduota.view.MyDivider;

import java.util.ArrayList;

public class HomeActivity extends MActivity
        implements NavigationView.OnNavigationItemSelectedListener, Constant, FutureCallback {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    private RecyclerView lv_list;
    private ItemAdapter itemAdapter;
    private ArrayList<Datum> items;
    private String token;
    private int page = 1;
    private LinearLayoutManager linearLayoutManager;

    private FutureCallback<Item> futureCallback;

    private BroadcastReceiver onNotificationComing;
    private IntentFilter intentFilter;


    public String getUrl(int page) {
        showLoading();
        String url = Constant.URL_ALL_ITEM + "page=" + page;
        Log.e("URL", url);
        return url;
    }

    public String getItemByStatus(int type, int page) {
        showLoading();
        String url = Constant.URL_GET_ITEM_BY_STATUS + type + "?page=" + page;
        Log.e("getItemByStatus", url);
        return url;
    }

    private NavigationView navigationView;

    private Spinner spinner_type_item;

    private final int ALL_ = 0;
    private final int ACTIVE_ = 1;
    private final int SOLD_ = 2;
    private final int PENDING_ = 3;
    private final int DRAFT_ = 4;
    private final int REJECT_ = 5;

    private int currentType = ALL;

    @Override
    protected void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        token = SharePrefManager.getInstance(this).getAccessToken();
        showLoading();
        updateFCM();

        futureCallback = this;
        //ION.getDataWithToken(this, token, getUrl(page), Item.class, this);

        navigationView = findViewById(R.id.nav_view);

        View header_view = navigationView.getHeaderView(0);

        TextView tv_email = header_view.findViewById(R.id.tvEmail);
        TextView tvName = header_view.findViewById(R.id.tvFullName);
        TextView tvCredit = header_view.findViewById(R.id.tv_credit);
        User user = sharePrefManager.getUser();

        tv_email.setText(user.getEmail());
        tvName.setText(user.getName());
        tvCredit.setText(getString(R.string.tv_credit) + " : " + user.getCredit());

        lv_list = findViewById(R.id.lv_list);

        items = new ArrayList<>();

        itemAdapter = new ItemAdapter(this, items);
        lv_list.setAdapter(itemAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        lv_list.setLayoutManager(linearLayoutManager);

        lv_list.addItemDecoration(new MyDivider(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity(AddAC.class);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_all);
        //navigationView.getMenu().getItem(0).getSubMenu().getItem(0).setChecked(true);

//        spinner_type_item = (Spinner) findViewById(R.id.spinner_type);
//
//        spinner_type_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String type = adapterView.getAdapter().getItem(i).toString();
//                Log.e("TYPE", type + " " + i);
//                page = 1;
//
//                switch (i) {
//
//                    case ALL_:
//
//                        ION.getDataWithToken(HomeActivity.this, token, getUrl(page), Item.class, futureCallback);
//                        break;
//
//                    case ACTIVE_:
//
//                        currentType = ACTIVE;
//                        getDataWithType(ACTIVE);
//                        break;
//
//                    case SOLD_:
//
//                        currentType = SOLD;
//                        getDataWithType(SOLD);
//                        break;
//
//                    case PENDING_:
//
//                        currentType = PENDING;
//                        getDataWithType(PENDING);
//                        break;
//
//                    case DRAFT_:
//
//                        currentType = DRAFT;
//                        getDataWithType(DRAFT);
//                        break;
//
//                    case REJECT_:
//
//                        currentType = REJECT;
//                        getDataWithType(REJECT);
//                        break;
//
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }


    public void getDataWithType(int type) {
        showLoading();
        ION.getDataWithToken(HomeActivity.this, token, getItemByStatus(type, page), Item.class, futureCallback);
    }


    @Override
    public void onCompleted(Exception e, Object object) {
        super.onCompleted(e, object);
        hideLoading();
        if (e != null) {
            return;
        }
        Item result = (Item) object;
        if (result.getError() != null) {
            showToast(getString(R.string.notify_out_of_session));
            startActivity(new Intent(this, LoginActivity.class));
            SharePrefManager.getInstance(this).removeAll();
            finish();
            return;
        }

        Log.e("DATA", new Gson().toJson(result));

        if (result != null) {
            if (result.getData() != null & result.getData().size() > 0) {
                if (result.getData().size() < 5) {
                    lv_list.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                        }
                    });
                } else {
                    lv_list.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        @Override
                        public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                            page++;
                            switch (currentType) {

                                case ACTIVE:

                                    currentType = ACTIVE;
                                    getDataWithType(ACTIVE);
                                    break;

                                case SOLD:

                                    currentType = SOLD;
                                    getDataWithType(SOLD);

                                    break;
                                case PENDING:

                                    currentType = PENDING;
                                    getDataWithType(PENDING);

                                    break;
                                case DRAFT:

                                    currentType = DRAFT;
                                    getDataWithType(DRAFT);

                                    break;

                                case REJECT:

                                    currentType = REJECT;
                                    getDataWithType(REJECT);
                                    break;

                                case ALL:
                                    ION.getDataWithToken(getApplicationContext(), token, getUrl(page), Item.class, futureCallback);
                                    break;

                            }
                        }
                    });


                }
                for (int i = 0; i < result.getData().size(); i++) {
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
                }
                items.addAll(result.getData());
                itemAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification) {

            startNewActivity(NotificationAC.class);
            return true;
        }

        if (id == R.id.action_message) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        unCheckAllMenuItems(navigationView.getMenu());
        item.setChecked(true);

        if (id == R.id.nav_all) {
            // Handle the camera action

        } else if (id == R.id.nav_order) {
            startNewActivity(OrderAC.class);
        } else if (id == R.id.nav_setting) {
            startNewActivity(SettingAC.class);
        } else if (id == R.id.nav_log_out) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SharePrefManager.getInstance(this).clearAccessToken();
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_charge) {
            startNewActivity(ChargerAC.class);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void unCheckAllMenuItems(@NonNull final Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            final MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                // Un check sub menu items
                unCheckAllMenuItems(item.getSubMenu());
            } else {
                item.setChecked(false);
            }
        }
    }

    public void updateFCM() {
        String fcm = FirebaseInstanceId.getInstance().getToken();
        Log.e("fcm", fcm);
        if (fcm != null) {
            ION.postFormDataWithToken(this, URL_SET_FCM_TOKEN, token, ION.fcmUpdate(fcm), new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.e("AAA", e.toString());
                }
            });
        }
    }
}
