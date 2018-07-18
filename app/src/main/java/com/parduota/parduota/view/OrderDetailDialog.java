package com.parduota.parduota.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.parduota.parduota.R;
import com.parduota.parduota.ion.Constant;

public class OrderDetailDialog extends DialogFragment implements Constant {


    private TextView tvTitle;
    private Button btnViewInEbay;
    private TextView tvDescription;


    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    private Intent intent;


    public static OrderDetailDialog instance(Intent intent) {
        OrderDetailDialog orderDetailDialog = new OrderDetailDialog();
        orderDetailDialog.setIntent(intent);
        return orderDetailDialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_extend_order_detail, container, false);

        tvTitle = view.findViewById(R.id.tvTitle);
        btnViewInEbay = view.findViewById(R.id.btnViewInEbay);
        tvDescription = view.findViewById(R.id.tvDescription);


        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (intent == null) return;

        String title = intent.getStringExtra(TITLE);
        final String link = intent.getStringExtra(LINK);
        String des = intent.getStringExtra(DESCRIPTION);

        tvTitle.setText(title);
        btnViewInEbay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(link));
                    startActivity(i);

                } catch (Exception ignored) {

                }
            }
        });

        tvDescription.setText(des);


    }
}
