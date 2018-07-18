package com.parduota.parduota.add;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.parduota.parduota.AddAC;
import com.parduota.parduota.R;
import com.parduota.parduota.abtract.MFragment;
import com.parduota.parduota.model.UploadItem;
import com.parduota.parduota.model.User;
import com.parduota.parduota.utils.SharePrefManager;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Objects;

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
    private SearchableSpinner tvCountry;
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

    public SearchableSpinner getTvCountry() {
        return tvCountry;
    }

    public EditText getTvAddress() {
        return tvAddress;
    }


    @Override
    protected void initView(View view) {
        tvPrice = view.findViewById(R.id.tv_price);
        tvQuality = view.findViewById(R.id.tv_quality);
        tvCondition = view.findViewById(R.id.tv_condition);
        tvCountry = view.findViewById(R.id.tv_country);
        tvAddress = view.findViewById(R.id.tv_address);


        try {
            SharePrefManager sharePrefManager = SharePrefManager.getInstance(getContext());
            User user = sharePrefManager.getUser();
            tvAddress.setText(user.getAddress());
            tvCountry.setSelection(positionSelected(user.getCountry()));


            AddAC addAC = (AddAC) getActivity();
            String data = (addAC).getData();

            if (data != null) {
                UploadItem uploadItem = addAC.getUploadItem();
                tvPrice.setText(uploadItem.getPrice());
                tvQuality.setText(uploadItem.getQuantity());
                tvAddress.setText(uploadItem.getLocation());
                tvCondition.setSelection(positionSelected(uploadItem.getCondition()));
                tvCountry.setSelection(positionCountrySelected(uploadItem.getCountry()));
            }


        } catch (Exception e) {

            Log.e("ACB", e.toString());
        }
    }

    @Override
    protected void setData(View view) {


    }

    private String getCountryByCode(String country) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.country)) {
            i++;
            if (cc.equals(country))
                break;
        }
        return getResources().getStringArray(R.array.country_code)[i];
    }


    private int positionSelected(String code) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.condition_code)) {
            i++;
            if (cc.equals(code.trim()))
                break;
        }
        Log.e("POSITION", i + "");
        return i;
    }

    private int positionCountrySelected(String code) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.country_code)) {
            i++;
            if (cc.equals(code.trim()))
                break;
        }
        Log.e("POSITION", i + "");
        return i;
    }


}
