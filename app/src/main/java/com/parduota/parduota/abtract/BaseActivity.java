package com.parduota.parduota.abtract;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.ChargeAC;
import com.parduota.parduota.ChatAC;
import com.parduota.parduota.ItemAC;
import com.parduota.parduota.LoginActivity;
import com.parduota.parduota.MainAC;
import com.parduota.parduota.NotificationAC;
import com.parduota.parduota.OrderAC;
import com.parduota.parduota.R;
import com.parduota.parduota.SettingAC;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.notification.Notification;
import com.parduota.parduota.utils.SharePrefManager;

import org.json.JSONObject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public abstract class BaseActivity extends BossAC
        implements NavigationView.OnNavigationItemSelectedListener, FutureCallback, Constant {


    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;


    private TextView notificationCounter;

    private BroadcastReceiver notifyUpdateCounter;

    /**
     * Helper method that can be used by child classes to
     * specify that they don't want a {@link Toolbar}
     *
     * @return true
     */
    private boolean useToolbar() {
        return true;
    }


    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     *
     * @return
     */
    private boolean useDrawerToggle() {
        return true;
    }


    protected void updateFCM() {
        String fcm = FirebaseInstanceId.getInstance().getToken();
        //Log.e("fcm", fcm);
        if (fcm != null) {
            ION.postFormDataWithToken(this, URL_SET_FCM_TOKEN, sharePrefManager.getAccessToken(), ION.fcmUpdate(fcm), new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                }
            });
        }
    }

    @Override
    public void setContentView(int layoutResID) {

        super.setContentView(layoutResID);

        getActionBarToolbar();

        setupNavDrawer();

        notifyUpdateCounter = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                alertCount = sharePrefManager.getCountNotification();
                updateAlertIcon();
            }
        };

        IntentFilter intentFilter = new IntentFilter(COMMING_MESSAGE);
        registerReceiver(notifyUpdateCounter, intentFilter);

        ION.getDataWithToken(getApplicationContext(), sharePrefManager.getAccessToken(), Constant.URL_GET_NOTIFICATION + 1, Notification.class, new FutureCallback<Notification>() {
            @Override
            public void onCompleted(Exception e, Notification result) {
                try {
                    sharePrefManager.setCountNotification(Integer.parseInt(result.getNew_()));
                    alertCount = Integer.parseInt(result.getNew_());
                    //Log.e("ABC", alertCount + "");
                    updateAlertIcon();
                } catch (Exception ignored) {

                }
            }
        });


    }//end setContentView

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (notifyUpdateCounter != null) unregisterReceiver(notifyUpdateCounter);
    }

    protected SharePrefManager sharePrefManager;

    private Activity context;
    private Dialog networkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Global methods as
        sharePrefManager = SharePrefManager.getInstance(this);
        context = this;
        Dialog dialog = new Dialog(context,
                R.style.AppTheme_NoActionBar);
        dialog.setContentView(R.layout.loadding);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressDialog progressDialog = new ProgressDialog(this);


        notifyUpdateCounter = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        IntentFilter intentFilter = new IntentFilter(UPDATE_COUNTER);
        //registerReceiver(notifyUpdateCounter, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription("ABC");
                //setSupportActionBar(mActionBarToolbar);

                if (useToolbar()) {
                    setSupportActionBar(mActionBarToolbar);
                } else {
                    mActionBarToolbar.setVisibility(View.GONE);
                }

            }
        }

    }


    private void setupNavDrawer() {


        mDrawerLayout = findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }


        // use the hamburger menu
        if (useDrawerToggle()) {
            ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
//                    .getDrawable(this, R.drawable.ba));
        }


        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        notificationCounter = (TextView) MenuItemCompat.getActionView(mNavigationView.getMenu().
                findItem(R.id.nav_notification));

        //initializeCountDrawer();

    }

    private void initializeCountDrawer() {
        notificationCounter.setGravity(Gravity.CENTER_VERTICAL);
        notificationCounter.setTypeface(null, Typeface.BOLD);
        notificationCounter.setTextColor(getResources().getColor(R.color.colorAccent));
        notificationCounter.setText("99+");

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log_out) {
            startActivity(new Intent(this, LoginActivity.class));
            sharePrefManager.clearAccessToken();
            finish();
            return true;

        }


        switch (id) {
            case R.id.nav_home:

                if (!(this instanceof MainAC))
                    createBackStack(new Intent(this, MainAC.class));
                break;

//            case R.id.nav_notification:
//                createBackStack(new Intent(this, NotificationAC.class));
//                break;

            case R.id.nav_all:
                createBackStack(new Intent(this, ItemAC.class));
                break;


            case R.id.nav_setting:

                createBackStack(new Intent(this, SettingAC.class));

                break;


            case R.id.nav_order:

                createBackStack(new Intent(this, OrderAC.class));

                break;


            case R.id.nav_charge:

                createBackStack(new Intent(this, ChargeAC.class));

                break;

            case R.id.nav_chat:

                createBackStack(new Intent(this, ChatAC.class));

                break;


            case R.id.nav_log_out:

                LoginManager.getInstance().logOut();
                startActivity(new Intent(this, LoginActivity.class));

                String fcm = FirebaseInstanceId.getInstance().getToken();
                //Log.e("fcm", fcm);
                if (fcm != null) {
                    ION.postFormDataWithToken(this, URL_SET_FCM_TOKEN, sharePrefManager.getAccessToken(), ION.fcmUpdate(""), new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                        }
                    });
                }

                sharePrefManager.clearAccessToken();

                finish();

                break;
        }

        closeNavDrawer();
        //overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);

        return true;
    }


    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }


    }


    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     *
     * @param intent
     */
    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onCompleted(Exception e, Object result) {
        if (e != null) {
            Log.e("ERROR", e.toString());
            showToast(getString(R.string.notify_internet_connection));
            return;
        }
        try {
            Log.e("DATA_SUPER", new Gson().toJson(result));
            JSONObject jsonObject = new JSONObject(new Gson().toJson(result));
            String error = jsonObject.getString(ERROR);
            if (error != null) {
                showToast(getString(R.string.notify_out_of_session));
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SharePrefManager.getInstance(this).removeAll();
                finish();
            }

        } catch (Exception ignored) {

        }

    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {


        final MenuItem alertMenuItem = menu.findItem(R.id.action_notification);


        View view = alertMenuItem.getActionView();
        frameLayout = view.findViewById(R.id.view_alert_red_circle);


        countTextView = frameLayout.findViewById(R.id.view_alert_count_textview);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onOptionsItemSelected(alertMenuItem);

            }
        });


        alertCount = sharePrefManager.getCountNotification();
        updateAlertIcon();


        return super.onPrepareOptionsMenu(menu);
    }

    private FrameLayout frameLayout;

    private int alertCount = -1;

    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle

        if (0 < alertCount) {
            countTextView.setText(String.valueOf(alertCount));
        }

        frameLayout.setVisibility((alertCount > 0) ? VISIBLE : GONE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_notification:

                startActivityForResult(new Intent(this, NotificationAC.class), 777);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private TextView countTextView;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {

            alertCount = sharePrefManager.getCountNotification();
            updateAlertIcon();

        }
    }
}//end BaseActivity
