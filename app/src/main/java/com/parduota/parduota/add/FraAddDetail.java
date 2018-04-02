package com.parduota.parduota.add;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;

/**
 * Created by MAC2015 on 11/2/17.
 */

public class FraAddDetail extends MFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_detail;
    }

    private EditText etDetail;

    public EditText getEtDetail() {
        return etDetail;
    }

    @Override
    protected void initView(View view) {
        etDetail = (EditText) view.findViewById(R.id.et_detail);
    }

    @Override
    protected void setData(View view) {

    }
}
