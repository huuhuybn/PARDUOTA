package com.parduota.parduota.add;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class FraAddDescription extends MFragment {
    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_desc;
    }

    private EditText tvPrice;
    private EditText tvQuality;
    private Spinner tvCondition;
    private Spinner tvCountry;
    private EditText tvAddress;

    public EditText getTvPrice() {
        return tvPrice;
    }

    public EditText getTvQuality() {
        return tvQuality;
    }

    public Spinner getTvCondition() {
        return tvCondition;
    }

    public Spinner getTvCountry() {
        return tvCountry;
    }

    public EditText getTvAddress() {
        return tvAddress;
    }

    @Override
    protected void initView(View view) {
        tvPrice = (EditText) view.findViewById(R.id.tv_price);
        tvQuality = (EditText) view.findViewById(R.id.tv_quality);
        tvCondition = (Spinner) view.findViewById(R.id.tv_condition);
        tvCountry = (Spinner) view.findViewById(R.id.tv_country);
        tvAddress = (EditText) view.findViewById(R.id.tv_address);
    }

    @Override
    protected void setData(View view) {


    }


}
