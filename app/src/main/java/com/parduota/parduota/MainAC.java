package com.parduota.parduota;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.BaseActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;

public class MainAC extends BaseActivity implements Constant {


    private User user;


    private TextView tv_email, tv_name, tv_credit;

    private TextView tv_notify_get_verify;
    private Button btn_get_verify;

    private View layout_request_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateFCM();

        layout_request_verify = findViewById(R.id.layout_request_verify);

        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(0).setChecked(true);

        user = sharePrefManager.getUser();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        tv_email = headerView.findViewById(R.id.tvEmail);
        tv_name = headerView.findViewById(R.id.tvFullName);
        tv_credit = headerView.findViewById(R.id.tv_credit);
        tv_notify_get_verify = findViewById(R.id.tv_notify_get_verify);

        btn_get_verify = findViewById(R.id.btnGetVerify);

        int verifyStatus = sharePrefManager.getVerifyStatus();


        if (verifyStatus == VERIFY_ADMIN) {

            // hide all form and notify confirm email
            layout_request_verify.setVisibility(View.GONE);
            findViewById(R.id.layout_confirm_email).setVisibility(View.GONE);



        } else if (verifyStatus == VERIFY_EMAIL) {

            layout_request_verify.setVisibility(View.VISIBLE);

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

                } else {

                }

            }

        } else if (verifyStatus == VERIFY_NOTHING) {
            findViewById(R.id.layout_confirm_email).setVisibility(View.VISIBLE);


        }


        // user try catch to avoid crash app

        try {

            tv_email.setText(user.getEmail());


        } catch (Exception e) {

            tv_email.setText(sharePrefManager.getAccountLogin());

        }

        try {

            tv_name.setText(user.getName());
            tv_name.setText(user.getFullName());

        } catch (Exception e) {

        }
        try {

            tv_credit.setText(getString(R.string.tv_credit) + " : " + user.getCredit());

        } catch (Exception e) {

        }


    }


    public void setUserNotApprove() {
        tv_notify_get_verify.setText(getString(R.string.notify_get_verify_done));
        btn_get_verify.setVisibility(View.GONE);
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

    public void updateFCM() {
        String fcm = FirebaseInstanceId.getInstance().getToken();
        Log.e("fcm", fcm);
        if (fcm != null) {
            ION.postFormDataWithToken(this, URL_SET_FCM_TOKEN, sharePrefManager.getAccessToken(), ION.fcmUpdate(fcm), new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                }
            });
        }
    }
}

