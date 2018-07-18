package com.parduota.parduota;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.parduota.parduota.abtract.BaseActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.User;
import com.parduota.parduota.view.NotifyTermAndConditionDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainAC extends BaseActivity implements Constant {


    private TextView tv_notify_get_verify;
    private Button btn_get_verify;

    private BroadcastReceiver updateCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateFCM();

        if (Constant.isDEBUG) Log.e("token",sharePrefManager.getAccessToken());


        View layout_request_verify = findViewById(R.id.layout_request_verify);

        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(0).setChecked(true);

        User user = sharePrefManager.getUser();
        if (Constant.isDEBUG) Log.e("User Main", new Gson().toJson(user));

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView tv_email = headerView.findViewById(R.id.tvEmail);
        TextView tv_name = headerView.findViewById(R.id.tvFullName);
        TextView tv_credit = headerView.findViewById(R.id.tv_credit);
        tv_notify_get_verify = findViewById(R.id.tv_notify_get_verify);

        btn_get_verify = findViewById(R.id.btnGetVerify);

        int verifyStatus = sharePrefManager.getVerifyStatus();


        // if user haven't accept our terms and condition yet. show dialog
        if (user.getTerm_accept() == 0) {

            FragmentManager fm = getSupportFragmentManager();
            NotifyTermAndConditionDialog notifyTermAndConditionDialog = new NotifyTermAndConditionDialog();
            // User must accept this dialog to go
            notifyTermAndConditionDialog.setCancelable(false);
            notifyTermAndConditionDialog.show(fm, "editNameDialogFragment");

        }


        switch (verifyStatus) {
            case VERIFY_ADMIN:

                findViewById(R.id.normal_user_layout).setVisibility(VISIBLE);
                // hide all form and notify confirm email
                layout_request_verify.setVisibility(GONE);
                findViewById(R.id.layout_confirm_email).setVisibility(GONE);


                break;
            case VERIFY_EMAIL:

                layout_request_verify.setVisibility(VISIBLE);

                if (user.getRequest_vip().equals(REQUEST_NO_VIP)) {

                    tv_notify_get_verify.setText(getString(R.string.notify_get_verify));

                    // if user has not get verify yet. set onClick to request VIP
                    btn_get_verify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivityForResult(new Intent(MainAC.this, GetVerifyAC.class), 999);
                        }
                    });

                } else {
                    if (user.getVip() == USER_NOT_APPROVED) {
                        setUserNotApprove();
                    }
                }

                break;
            case VERIFY_NOTHING:

                findViewById(R.id.layout_confirm_email).setVisibility(VISIBLE);

                break;
        }

        // user try catch to avoid crash app

        try {

            if (!user.getEmail().contains("domain"))
                tv_email.setText(user.getEmail());


        } catch (Exception ignored) {

        }

        try {

            tv_name.setText(user.getName());

        } catch (Exception ignored) {

        }

        try {

            tv_credit.setText(getString(R.string.tv_credit) + " : " + user.getCredit());
            tv_credit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainAC.this, BuyCreditAC.class));
                }
            });

        } catch (Exception ignored) {

        }

        updateCredit = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        IntentFilter intentFilter = new IntentFilter(URL_UPDATE_CREDIT);
        registerReceiver(updateCredit, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateCredit != null) {
            unregisterReceiver(updateCredit);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUserNotApprove() {
        tv_notify_get_verify.setText(getString(R.string.notify_get_verify_done));
        btn_get_verify.setVisibility(GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 999) {
                setUserNotApprove();
            }
        }

    }


}

