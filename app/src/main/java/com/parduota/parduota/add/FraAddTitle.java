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

public class FraAddTitle extends MFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_title;
    }

    private EditText etTitle;
    private TextView tv_counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    private int counter = 0;

    public EditText getEtTitle() {
        return etTitle;
    }

    @Override
    protected void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        tv_counter = view.findViewById(R.id.tv_counter);

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = etTitle.length();
                counter = length;
                String convert = String.valueOf(length);

                if (length >= 500) {
                    tv_counter.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tv_counter.setTextColor(getResources().getColor(R.color.black));
                }
                tv_counter.setText(convert + "/500");
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
        AddAC addAC = (AddAC) getActivity();
        UploadItem uploadItem = (addAC).getUploadItem();

        try {
            etTitle.setText(uploadItem.getTitle());
        } catch (Exception ignored) {

        }


    }

}
