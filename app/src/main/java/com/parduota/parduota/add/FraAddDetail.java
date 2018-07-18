package com.parduota.parduota.add;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parduota.parduota.AddAC;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.model.UploadItem;

import java.util.Objects;

/**
 * Created by MAC2015 on 11/2/17.
 */

public class FraAddDetail extends MFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_detail;
    }

    private EditText etDetail;

    private TextView tvCounter;
    private int counter = 0;


    public EditText getEtDetail() {
        return etDetail;
    }

    @Override
    protected void initView(View view) {

        etDetail = view.findViewById(R.id.et_detail);
        tvCounter = view.findViewById(R.id.tv_counter);
        try {

            AddAC addAC = (AddAC) getActivity();
            UploadItem uploadItem = (addAC).getUploadItem();
            etDetail.setText(uploadItem.getDescription());

        } catch (Exception ignored) {

        }

        etDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = etDetail.length();
                counter = length;
                String convert = String.valueOf(length);

                if (length >= 1500) {
                    etDetail.setTextColor(getResources().getColor(R.color.red));
                } else {
                    etDetail.setTextColor(getResources().getColor(R.color.black));
                }
                tvCounter.setText(convert + "/1500");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void setData(View view) {

    }

    public int getCounter() {
        return counter;
    }
}
