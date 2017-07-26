package com.parduota.parduota.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;
import com.parduota.parduota.utils.SharePrefManager;

/**
 * Created by huy_quynh on 6/13/17.
 */

public class FraDashBoard extends MFragment implements Constant {


    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver receiverCredit;

    @Override
    protected int setLayoutId() {
        return R.layout.fra_dash_board;
    }

    private User user;

    private TextView tv_name, tv_credit, tv_total, tv_email;

    @Override
    protected void initView(View view) {
        user = SharePrefManager.getInstance(getActivity()).getUser();
        view.findViewById(R.id.btn_request_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_credit = (TextView) view.findViewById(R.id.tv_credit);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String data = intent.getStringExtra(DATA);
                if (data != null) tv_total.setText(data);

            }
        };
        IntentFilter intentFilter = new IntentFilter(UPDATE_TOTAL);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);

        receiverCredit = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String payCode = intent.getStringExtra(DATA);
                updateCredit(payCode);

            }
        };
        IntentFilter intentFilter1 = new IntentFilter(CREDIT);
        getActivity().registerReceiver(receiverCredit, intentFilter1);
    }


    public void updateCredit(String payCode) {
        showToast("Updating Credit...!");
        String token = SharePrefManager.getInstance(getActivity()).getAccessToken();
        ION.postDataWithToken(getActivity(), URL_UPDATE_CREDIT, token, ION.updateCredit(payCode), Object.class, new FutureCallback() {
            @Override
            public void onCompleted(Exception e, Object result) {
                Log.e("Data", result.toString());
                User user_ = new Gson().fromJson(result.toString(), User.class);
                tv_credit.setText(user_.getCredit());
            }
        });
    }

    @Override
    protected void setData(View view) {
        String name = user.getName();
        String credit = user.getCredit();
        String email = user.getEmail();
        if (tv_name != null) tv_name.setText(name);
        if (credit != null) tv_credit.setText(credit);
        if (email != null) tv_email.setText(email);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
        getActivity().unregisterReceiver(receiverCredit);
    }
}
