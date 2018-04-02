package com.parduota.parduota.add;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parduota.parduota.AddAC;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;

/**
 * Created by MAC2015 on 11/2/17.
 */

public class FraAddTitle extends MFragment {


    @Override
    protected int setLayoutId() {
        return R.layout.fra_add_title;
    }

    public EditText etTitle;

    public EditText getEtTitle() {
        return etTitle;
    }

    @Override
    protected void initView(View view) {
        etTitle = (EditText) view.findViewById(R.id.et_title);
    }

    @Override
    protected void setData(View view) {

    }

}
