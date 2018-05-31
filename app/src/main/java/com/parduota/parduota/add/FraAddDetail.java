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

        etDetail =  view.findViewById(R.id.et_detail);
        try {

            AddAC addAC = (AddAC) getActivity();
            UploadItem uploadItem = Objects.requireNonNull(addAC).getUploadItem();
            etDetail.setText(uploadItem.getDescription());

        }catch (Exception ignored){

        }
    }

    @Override
    protected void setData(View view) {

    }
}
