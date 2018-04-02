package com.parduota.parduota.add;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class FraAddDimension extends MFragment {

    @Override
    protected int setLayoutId() {
        return R.layout.fra_dimension;
    }


    private CheckBox cbSellForCharity;
    private RadioGroup rgShipping;
    private RadioButton rbPickUpOnly;
    private RadioButton rbFreight;
    private RadioButton rbKgDimension;
    private EditText etWidth;
    private EditText etHeight;
    private EditText etWeight;
    private EditText length;


    @Override
    protected void initView(View view) {
        cbSellForCharity = (CheckBox) view.findViewById(R.id.cb_sell_for_charity);
        rgShipping = (RadioGroup) view.findViewById(R.id.rg_shipping);
        rbPickUpOnly = (RadioButton) view.findViewById(R.id.rb_pick_up_only);
        rbFreight = (RadioButton) view.findViewById(R.id.rb_freight);
        rbKgDimension = (RadioButton) view.findViewById(R.id.rb_kg_dimension);
        etWidth = (EditText) view.findViewById(R.id.et_width);
        etHeight = (EditText) view.findViewById(R.id.et_height);
        etWeight = (EditText) view.findViewById(R.id.et_weight);
        length = (EditText) view.findViewById(R.id.length);

    }

    @Override
    protected void setData(View view) {

    }

    public CheckBox getCbSellForCharity() {
        return cbSellForCharity;
    }

    public void setCbSellForCharity(CheckBox cbSellForCharity) {
        this.cbSellForCharity = cbSellForCharity;
    }

    public RadioGroup getRgShipping() {
        return rgShipping;
    }

    public void setRgShipping(RadioGroup rgShipping) {
        this.rgShipping = rgShipping;
    }

    public RadioButton getRbPickUpOnly() {
        return rbPickUpOnly;
    }

    public void setRbPickUpOnly(RadioButton rbPickUpOnly) {
        this.rbPickUpOnly = rbPickUpOnly;
    }

    public RadioButton getRbFreight() {
        return rbFreight;
    }

    public void setRbFreight(RadioButton rbFreight) {
        this.rbFreight = rbFreight;
    }

    public RadioButton getRbKgDimension() {
        return rbKgDimension;
    }

    public void setRbKgDimension(RadioButton rbKgDimension) {
        this.rbKgDimension = rbKgDimension;
    }

    public EditText getEtWidth() {
        return etWidth;
    }

    public void setEtWidth(EditText etWidth) {
        this.etWidth = etWidth;
    }

    public EditText getEtHeight() {
        return etHeight;
    }

    public void setEtHeight(EditText etHeight) {
        this.etHeight = etHeight;
    }

    public EditText getEtWeight() {
        return etWeight;
    }

    public void setEtWeight(EditText etWeight) {
        this.etWeight = etWeight;
    }

    public EditText getLength() {
        return length;
    }

    public void setLength(EditText length) {
        this.length = length;
    }
}
