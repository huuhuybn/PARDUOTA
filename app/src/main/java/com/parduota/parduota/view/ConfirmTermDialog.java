package com.parduota.parduota.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.User;
import com.parduota.parduota.utils.SharePrefManager;

import java.util.Objects;

/**
 * Created by huy_quynh on 7/6/17.
 */

public class ConfirmTermDialog extends Dialog {


    private TextView term;

    public ConfirmTermDialog(@NonNull Context context) {
        super(context);
        init();
        Context context1 = context;
    }

    public ConfirmTermDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ConfirmTermDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    private ProgressBar progressBar;

    private void init() {
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_confirm);

        term = findViewById(R.id.term);
        Button btnAccept = findViewById(R.id.btn_accept);

        progressBar = findViewById(R.id.progress_bar);

        Ion.with(getContext().getApplicationContext()).load(Constant.URL_GET_TERMS).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {

                try {
                    String data = result.get("data").getAsString();
                    term.setText(Html.fromHtml(data));
                } catch (Exception ignored) {
                }
                progressBar.setVisibility(View.GONE);

            }
        });


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharePrefManager sharePrefManager = SharePrefManager.getInstance(getContext().getApplicationContext());
                Ion.with(getContext().getApplicationContext()).load(Constant.URL_ACCECPT_TERM).setHeader(ION.authHeader(sharePrefManager.getAccessToken())).setBodyParameter("", "").asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        try {
                            User user = sharePrefManager.getUser();
                            user.setTerm_accept(1);
                            sharePrefManager.saveUser(user);
                            dismiss();
                        } catch (Exception ignored) {

                        }
                    }
                });

            }
        });

    }

}
