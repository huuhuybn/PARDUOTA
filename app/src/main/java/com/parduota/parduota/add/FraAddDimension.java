package com.parduota.parduota.add;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parduota.parduota.AddAC;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.model.UploadItem;

import java.util.Objects;

/**
 * Created by MAC2015 on 11/8/17.
 */

public class FraAddDimension extends MFragment implements Constant {

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
    private EditText etLength;


    @Override
    protected void initView(final View view) {
        cbSellForCharity = view.findViewById(R.id.cb_sell_for_charity);
        rgShipping = view.findViewById(R.id.rg_shipping);
        rbPickUpOnly = view.findViewById(R.id.rb_pick_up_only);
        rbFreight = view.findViewById(R.id.rb_freight);
        rbKgDimension = view.findViewById(R.id.rb_kg_dimension);
        etWidth = view.findViewById(R.id.et_width);
        etHeight = view.findViewById(R.id.et_height);
        etWeight = view.findViewById(R.id.et_weight);
        etLength = view.findViewById(R.id.length);

        rgShipping.getChildAt(0).setSelected(true);

        rgShipping.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int radioButton = radioGroup.getCheckedRadioButtonId();

                int thirdButton = radioGroup.getChildAt(2).getId();

                View expand = view.findViewById(R.id.expandLayout);
                if (radioButton == thirdButton) {
                    expand.setVisibility(View.VISIBLE);
                } else {
                    expand.setVisibility(View.GONE);
                }

            }
        });


        AddAC addAC = (AddAC) getActivity();
        if (Objects.requireNonNull(addAC).getData() != null) {
            UploadItem uploadItem = addAC.getUploadItem();

            if (uploadItem.getSell_for_charity().equals("1")) {
                cbSellForCharity.setChecked(true);
            }

            switch (uploadItem.getShipping_type_custom()) {

                case PICK_UP_ONLY:
                    rgShipping.check(R.id.rb_pick_up_only);
                    break;
                case FREIGHT:
                    rgShipping.check(R.id.rb_freight);
                    break;
                case KG_AND_DIMENSION:
                    rgShipping.check(R.id.rb_kg_dimension);
                    break;
            }
        }

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
        return etLength;
    }

    public void setLength(EditText length) {
        this.etLength = length;
    }
}
