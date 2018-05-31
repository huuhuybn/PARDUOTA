package com.parduota.parduota.add;

import android.view.View;
import android.widget.EditText;

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

    public EditText getEtTitle() {
        return etTitle;
    }

    @Override
    protected void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
    }

    @Override
    protected void setData(View view) {
        AddAC addAC = (AddAC) getActivity();
        UploadItem uploadItem = Objects.requireNonNull(addAC).getUploadItem();

        try {
            etTitle.setText(uploadItem.getTitle());
        } catch (Exception ignored) {

        }


    }

}
