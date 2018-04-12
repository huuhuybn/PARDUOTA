package com.parduota.parduota.abtract;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.LoginActivity;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.utils.SharePrefManager;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;


public abstract class MActivity extends AppCompatActivity implements FutureCallback, Constant {

    protected SharePrefManager sharePrefManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharePrefManager = SharePrefManager.getInstance(this);
        context = this;
        dialog = new Dialog(context,
                R.style.AppTheme_NoActionBar);
        dialog.setContentView(R.layout.loadding);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(setLayoutId());

        progressDialog = new ProgressDialog(this);
        initView();
    }

    public void showLoading() {
        if (progressDialog != null) {
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    private Dialog dialog;

    protected Activity context;
    private Dialog networkDialog;


    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null) progressDialog.dismiss();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, boolean animation) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (animation) {
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        //ft.replace(R.id.content, fragment);
        ft.setTransition(transition);
        if (addToBackStack)
            ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack,
                            int transition, boolean animation, String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (animation) {
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        //ft.replace(R.id.content, fragment);
        ft.setTransition(transition);
        if (addToBackStack)
            ft.addToBackStack(name);
        ft.commitAllowingStateLoss();

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    protected void startNewActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    protected void startNewActivityForResult(Class aClass,int code) {
        Intent intent = new Intent(this, aClass);
        startActivityForResult(intent,code);
    }

    protected void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    protected void showToast(int resourceStringId) {
        showToast(getString(resourceStringId));
    }

    /**
     * show warning for network
     */
    private void showAlertForNetwork() {
        try {
            if (networkDialog == null) {
                this.createNetworkDialog();
            }

            if (!networkDialog.isShowing()) {
                networkDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createNetworkDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context);
        alertDialog.setTitle(this.context.getResources().getString(
                R.string.network_unavailable));
        alertDialog.setMessage(this.context.getResources().getString(
                R.string.Please_turn_on_network_connection));
        alertDialog.setPositiveButton(
                this.context.getResources().getString(R.string.turn_on),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* call to network setting */
                        Intent settingsIntent = new Intent(
                                Settings.ACTION_SETTINGS);
                        context.startActivity(settingsIntent);
                    }
                });

        this.networkDialog = alertDialog.create();
    }

    /**
     * get view from layout resource
     *
     * @param layoutId layout resource id
     * @return view
     */
    public View getView(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null, false);
        return view;
    }


    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                return;
            }

        } catch (Exception e1) {

        }


    }
}
