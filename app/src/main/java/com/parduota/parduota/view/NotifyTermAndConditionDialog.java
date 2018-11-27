package com.parduota.parduota.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.User;
import com.parduota.parduota.remote.RetrofitRequest;
import com.parduota.parduota.remote.RetrofitClient;
import com.parduota.parduota.utils.SharePrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifyTermAndConditionDialog extends DialogFragment {

    private TextView term;
    private ProgressBar progressBar, progressBarClick;
    private Button btnAccept;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        term = view.findViewById(R.id.term);
        progressBar = view.findViewById(R.id.progress_bar);
        btnAccept = view.findViewById(R.id.btn_accept);

        progressBarClick = view.findViewById(R.id.progress_bar_click);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RetrofitRequest apiService =
                RetrofitClient.getClient(getActivity()).create(RetrofitRequest.class);


        apiService.getTermAndCondition().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    String data = response.body().get("data").getAsString();
                    term.setText(Html.fromHtml(data));
                } catch (Exception ignored) {
                    if (Constant.isDEBUG) Log.e("EXX", ignored.toString());
                }
                progressBar.setVisibility(View.GONE);
                btnAccept.setEnabled(true);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBarClick.setVisibility(View.VISIBLE);
                btnAccept.setEnabled(false);

                RetrofitRequest apiService =
                        RetrofitClient.getClient(getActivity()).create(RetrofitRequest.class);
                String token = SharePrefManager.getInstance(getContext()).getAccessToken();
                apiService.acceptTermAndCondition(RetrofitRequest.PRE_TOKEN + token).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (Constant.isDEBUG) Log.e("ACCEPT", response.body().toString());

                        try {

                            User user =
                                    SharePrefManager.getInstance(getContext()).getUser();
                            user.setTerm_accept(1);
                            SharePrefManager.getInstance(getContext()).saveUser(user);
                            dismiss();

                        } catch (Exception ignored) {

                        }

                        progressBarClick.setVisibility(View.GONE);
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressBarClick.setVisibility(View.GONE);
                        btnAccept.setEnabled(true);
                    }
                });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

}
