package com.parduota.parduota;

import android.view.View;
import android.widget.EditText;

import com.koushikdutta.async.Util;
import com.koushikdutta.async.future.FutureCallback;
import com.parduota.parduota.abtract.MActivity;
import com.parduota.parduota.ion.Constant;
import com.parduota.parduota.ion.ION;
import com.parduota.parduota.model.signup.SignUp;

/**
 * Created by huy_quynh on 6/19/17.
 */

public class SignUpAC extends MActivity implements FutureCallback, Constant {

    @Override
    protected int setLayoutId() {
        return R.layout.act_sign_up;
    }


    private EditText et_name, et_email, et_password;
    private FutureCallback futureCallback;

    @Override
    protected void initView() {
        futureCallback = this;

        et_name = (EditText) findViewById(R.id.et_name);

        et_email = (EditText) findViewById(R.id.et_email);

        et_password = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                ION.postData(SignUpAC.this, Constant.URL_SIGN_UP, ION.signUP(name, email, password), SignUp.class, futureCallback);
            }
        });

    }

    @Override
    public void onCompleted(Exception e, Object result) {
        super.onCompleted(e, result);
        SignUp signUp = (SignUp) result;
        if (signUp.getError() != null) {
            if (signUp.getError().getErrors() != null) {
                String message = signUp.getError().getErrors().getEmail().get(0);
                showToast(message);
                return;
            }
        }
        if (signUp.getStatus().equals(OK)) {

        }
    }
}
