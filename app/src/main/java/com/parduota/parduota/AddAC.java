package com.parduota.parduota;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.parduota.parduota.abtract.MActivity;

/**
 * Created by huy_quynh on 7/8/17.
 */

public class AddAC extends MActivity {


    private EditText et_title, et_des, et_price, et_quantity, et_address;
    private AutoCompleteTextView at_category;
    private Button btn_add;
    private int PICK_IMAGE = 999;

    @Override
    protected int setLayoutId() {
        return R.layout.act_add;
    }

    @Override
    protected void initView() {

        et_title = (EditText) findViewById(R.id.et_title);
        et_des = (EditText) findViewById(R.id.et_des);
        et_price = (EditText) findViewById(R.id.et_price);
        et_quantity = (EditText) findViewById(R.id.et_quantity);
        et_address = (EditText) findViewById(R.id.et_address);
        at_category = (AutoCompleteTextView) findViewById(R.id.category);

        btn_add = (Button) findViewById(R.id.btn_add);

        findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), PICK_IMAGE);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

            }
        }
    }
}
